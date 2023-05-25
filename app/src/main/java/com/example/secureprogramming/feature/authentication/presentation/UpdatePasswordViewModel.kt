package com.example.secureprogramming.feature.authentication.presentation

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.secureprogramming.core.utils.MySharedPreferences
import com.example.secureprogramming.feature.crud_password.data.repository.datasource.AddPasswordDataSource
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UpdatePasswordViewModel @Inject constructor(
    private val addPasswordDataSource: AddPasswordDataSource,
) : ViewModel() {

    var password = MutableLiveData<String>()
    val passwordError = MutableLiveData<String>()
    var newPassword = MutableLiveData<String>()
    val newPasswordError = MutableLiveData<String>()
    var confirmNewPassword = MutableLiveData<String>()
    val confirmNewPasswordError = MutableLiveData<String>()
    val signInEnabled = MutableLiveData<Boolean>()
    val responseMessage = MutableLiveData<Pair<String, Boolean>>()

    init {
        signInEnabled.value = false
    }


    fun validatePassword() {
        if (password.value.isNullOrBlank()) {
            passwordError.value = "Password cannot be empty"
        } else {
            passwordError.value = ""
        }
        resetPasswordEnabled()
    }


    fun validateNewPassword() {
        val PASSWORD_PATTERN = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}\$".toRegex()

        if (newPassword.value.isNullOrBlank()) {
            newPasswordError.value = "Password cannot be empty"
        } else if (!PASSWORD_PATTERN.matches(newPassword.value!!)) {
            newPasswordError.value = "Password must be at least 8 characters long and include at least one uppercase and one lowercase letter, at least one digit, and at least one special character"
        } else {
            newPasswordError.value = ""
        }
        resetPasswordEnabled()
    }


     fun validateNewConfirmPassword() {
        if (confirmNewPassword.value.isNullOrBlank()) {
            confirmNewPasswordError.value = "Password cannot be empty"
        } else if(confirmNewPassword.value != newPassword.value){
            confirmNewPasswordError.value = "Password mismatch"
        } else {
            confirmNewPasswordError.value = ""
        }
        resetPasswordEnabled()
    }

    private fun resetPasswordEnabled() {
        signInEnabled.value = newPasswordError.value == "" && passwordError.value == "" && confirmNewPasswordError.value == ""
    }

    fun resetPassword() {
        val user = FirebaseAuth.getInstance().currentUser
        val credential = EmailAuthProvider.getCredential(user?.email!!, password.value!!)

        user.reauthenticate(credential)
            .addOnCompleteListener { reauthTask ->
                if (reauthTask.isSuccessful) {
                    user.updatePassword(newPassword.value!!)
                        .addOnCompleteListener { updatePasswordTask ->
                            if (updatePasswordTask.isSuccessful) {
                                addPasswordDataSource.resetPassword(newPassword.value!!)
                            } else {
                                Result.success(Pair("Password reset successful", true))
                            }
                        }
                } else {
                    Result.success(Pair(reauthTask.exception, false))
                }
            }
    }

}