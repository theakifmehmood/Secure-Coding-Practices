package com.example.secureprogramming.feature.authentication.data.repository.datasource

import com.example.secureprogramming.feature.authentication.data.model.UserDataModel
import com.example.secureprogramming.feature.authentication.data.model.UserSignInRequestDataModel
import com.example.secureprogramming.feature.authentication.data.model.UserSignUpRequestDataModel
import javax.crypto.SecretKey

interface AuthenticationSource {

    suspend fun signIn(data : UserSignInRequestDataModel) : Result<UserDataModel>

    suspend fun signUp(data : UserSignUpRequestDataModel) : Result<UserDataModel>

    suspend fun logout()

    fun generateEncryptionKey(password: String)

}