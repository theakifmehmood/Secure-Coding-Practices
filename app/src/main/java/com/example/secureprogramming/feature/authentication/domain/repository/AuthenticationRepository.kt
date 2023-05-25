package com.example.secureprogramming.feature.authentication.domain.repository

import com.example.secureprogramming.feature.authentication.domain.model.UserDomainModel
import com.example.secureprogramming.feature.authentication.domain.model.UserSignInRequestDomainModel
import com.example.secureprogramming.feature.authentication.domain.model.UserSignUpRequestDomainModel
import javax.crypto.SecretKey

interface AuthenticationRepository {

    suspend fun signIn(data : UserSignInRequestDomainModel) : Result<UserDomainModel>


    suspend fun signUp(data : UserSignUpRequestDomainModel) : Result<UserDomainModel>


    suspend fun logout()

    fun generateEncryptionKey(password: String)
}