package com.example.secureprogramming.feature.crud_password.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.secureprogramming.feature.crud_password.domain.usercases.StorePasswordUseCase
import com.example.secureprogramming.feature.crud_password.presentation.mapper.PasswordDomainToPresentationMapper
import com.example.secureprogramming.feature.crud_password.presentation.mapper.PasswordPresentationToDomainMapper
import com.example.secureprogramming.feature.crud_password.presentation.model.PasswordPresentationModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddPasswordViewModel @Inject constructor(
    private val storePasswordUseCase: StorePasswordUseCase,
    private val passwordDomainToPresentationMapper: PasswordDomainToPresentationMapper,
    private val passwordPresentationToDomainMapper: PasswordPresentationToDomainMapper,
    ) : ViewModel() {




    val title = MutableLiveData<String>()
    val name = MutableLiveData<String>()
    val userName = MutableLiveData<String>()
    val link = MutableLiveData<String>()
    val password = MutableLiveData<String>()
    val note =  MutableLiveData<String>()
    val response =  MutableLiveData<Pair<Boolean, String> >()

    private val titleError  = MutableLiveData<String>()
    private val accountError  = MutableLiveData<String>()
    private val userNameError  = MutableLiveData<String>()
    private val websiteError  = MutableLiveData<String>()
    private val passwordError  = MutableLiveData<String>()
    private val noteError  =  MutableLiveData<String>()

    fun addPassword() {
    if (isDataValid()) {
            viewModelScope.launch {
                val result = storePasswordUseCase.storePassword(
                    passwordPresentationToDomainMapper.mapDomainToData(
                        PasswordPresentationModel(
                            title = title.value!!,
                            userName = userName.value!!,
                            password = password.value!!,
                            link = link.value!!,
                            note = note.value!!,
                        )
                    )

                )
                result.fold(
                    onSuccess = {
                        response.postValue(Pair(true, "Your data has been saved successfully!"))
                    },
                    onFailure = {
                        response.postValue(Pair(false, it.message?:""))
                    }
                )
            }
        }
    }


    fun updateValues(passwordData: PasswordPresentationModel){
         title.value = passwordData.title
         userName.value  = passwordData.userName
         link.value  = passwordData.link
         password.value  = passwordData.password
         note.value  = passwordData.note
    }


    // Regular expression to match alphabetic and numeric characters only
    val alphaNumericRegex = Regex("[a-zA-Z0-9]+")

    // Regular expression to match a valid URL
    val urlRegex = Regex("(http(s)?://)?([\\w-]+\\.)+[\\w-]+(/[\\w- ;,./?%&=]*)?")

    // Regular expression to match a valid password (at least one uppercase, one lowercase, one digit, and one special character)
    val passwordRegex = Regex("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[^\$@!%*?&])[A-Za-z\\d\$@!%*?&]{8,}")


    private fun isDataValid(): Boolean {
        return isTitleValid() && isUserNameValid() && isWebsiteValid() &&
                isPasswordValid() && isNoteValid()
    }

    private fun isTitleValid(): Boolean {
        val value = title.value?.trim()
        if (value.isNullOrEmpty()) {
            titleError.value = "Title cannot be empty"
            return false
        }
        titleError.value = ""
        return true
    }

    private fun isAccountValid(): Boolean {
        val value = name.value?.trim()
        if (value.isNullOrEmpty()) {
            accountError.value = "Account cannot be empty"
            return false
        }
        accountError.value = ""
        return true
    }

    private fun isUserNameValid(): Boolean {
        val value = userName.value?.trim()
        if (value.isNullOrEmpty()) {
            userNameError.value = "Username cannot be empty"
            return false
        }
        userNameError.value = ""
        return true
    }

    private fun isWebsiteValid(): Boolean {
        val value = link.value?.trim()
        if (value.isNullOrEmpty()) {
            websiteError.value = "Website cannot be empty"
            return false
        }
        websiteError.value = ""
        return true
    }

    private fun isPasswordValid(): Boolean {
        val value = password.value?.trim()
        if (value.isNullOrEmpty()) {
            passwordError.value = "Password cannot be empty"
            return false
        }
        passwordError.value = ""
        return true
    }

    private fun isNoteValid(): Boolean {
        val value = note.value?.trim()
        if (value.isNullOrEmpty()) {
            noteError.value = "Note cannot be empty"
            return false
        }
        noteError.value = ""
        return true
    }
}