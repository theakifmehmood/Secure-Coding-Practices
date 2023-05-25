package com.example.secureprogramming.feature.authentication.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.secureprogramming.feature.authentication.domain.model.UserSignInRequestDomainModel
import com.example.secureprogramming.feature.authentication.domain.usercases.GenerateEncryptionUseCase
import com.example.secureprogramming.feature.authentication.domain.usercases.SignInUseCase
import com.example.secureprogramming.feature.authentication.presentation.mapper.UserDomainToPresentationMapper
import com.example.secureprogramming.feature.authentication.presentation.model.UserPresentationModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val signInUseCase: SignInUseCase,
    private val generateEncryptionUseCase: GenerateEncryptionUseCase,
    private val userDomainToPresentationMapper: UserDomainToPresentationMapper

) : ViewModel() {

    var email = MutableLiveData<String>()
    var password = MutableLiveData<String>()
    val emailError = MutableLiveData<String>()
    val passwordError = MutableLiveData<String>()
    val signInEnabled = MutableLiveData<Boolean>()

    val waitTime = MutableLiveData<String>()

    val user = MutableLiveData<UserPresentationModel>()
    val exceptionMessage = MutableLiveData<String>()

    init {
        signInEnabled.value = false
    }

    fun validateEmail() {
        if (email.value.isNullOrBlank()) {
            emailError.value = "Email cannot be empty"
        } else if (!email.value!!.contains("@")) {
            emailError.value = "Invalid email format"
        } else {
            emailError.value = ""
        }
        updateSignInEnabled()
    }

    fun validatePassword() {
        if (password.value.isNullOrBlank()) {
            passwordError.value = "Password cannot be empty"
        } else {
            passwordError.value = ""
        }
        updateSignInEnabled()
    }

    private fun updateSignInEnabled() {
        signInEnabled.value = emailError.value == "" && passwordError.value == ""
    }

    fun signIn() {
        val emailValue = email.value
        val passwordValue = password.value

        if (!emailValue.isNullOrBlank() && !passwordValue.isNullOrBlank()) {

            CoroutineScope(Dispatchers.IO).launch {
                val result = signInUseCase.signIn(
                    UserSignInRequestDomainModel(emailValue, passwordValue)
                )
                result.fold(
                    onSuccess = {
                        generateEncryptionUseCase.generateEncryptionKey(password = passwordValue)
                        user.postValue(userDomainToPresentationMapper.mapDomainToPresentation(it))
                    },
                    onFailure = {
                        exceptionMessage.postValue(it.message)
                    }
                )
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        // Clean up any resources here
    }
}