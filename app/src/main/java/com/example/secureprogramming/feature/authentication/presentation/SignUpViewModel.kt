package com.example.secureprogramming.feature.authentication.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.secureprogramming.feature.authentication.domain.model.UserSignUpRequestDomainModel
import com.example.secureprogramming.feature.authentication.domain.usercases.GenerateEncryptionUseCase
import com.example.secureprogramming.feature.authentication.domain.usercases.SignUpUseCase
import com.example.secureprogramming.feature.authentication.presentation.mapper.UserDomainToPresentationMapper
import com.example.secureprogramming.feature.authentication.presentation.model.UserPresentationModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val signUpUseCase: SignUpUseCase,
    private val generateEncryptionUseCase: GenerateEncryptionUseCase,
    private val userDomainToPresentationMapper: UserDomainToPresentationMapper
) : ViewModel() {

    val name = MutableLiveData<String>()
    val email = MutableLiveData<String>()
    val password = MutableLiveData<String>()
    val nameError = MutableLiveData<String>()
    val emailError = MutableLiveData<String>()
    val passwordError = MutableLiveData<String>()
    val signUpEnabled = MutableLiveData<Boolean>()

    val waitTime = MutableLiveData<String>()

    val user =  MutableLiveData<UserPresentationModel>()
    val exceptionMessage =  MutableLiveData<String>()

    init {
        signUpEnabled.value = false
    }

    fun validateName() {
        if (name.value.isNullOrBlank()) {
            nameError.value = "Name cannot be empty"
        } else {
            nameError.value = ""
        }
        updateSignInEnabled()
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
        val PASSWORD_PATTERN = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}\$".toRegex()

        if (password.value.isNullOrBlank()) {
            passwordError.value = "Password cannot be empty"
        } else if (!PASSWORD_PATTERN.matches(password.value!!)) {
            passwordError.value = "Password must be at least 8 characters long and include at least one uppercase and one lowercase letter, at least one digit, and at least one special character"
        } else {
            passwordError.value = ""
        }
        updateSignInEnabled()
    }

    private fun updateSignInEnabled() {
        signUpEnabled.value = emailError.value == "" && passwordError.value == "" && nameError.value == ""
    }

    fun signUp() {
        val nameValue = name.value
        val emailValue = email.value
        val passwordValue = password.value

        if (!emailValue.isNullOrBlank() && !passwordValue.isNullOrBlank() && !nameValue.isNullOrBlank()) {
            CoroutineScope(Dispatchers.IO).launch {
                val result = signUpUseCase.signUp(
                    UserSignUpRequestDomainModel(name = nameValue, email = emailValue, password = passwordValue)
                )

                result.fold(
                    onSuccess = {
                        generateEncryptionUseCase.generateEncryptionKey(passwordValue)
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