package com.example.secureprogramming.feature.authentication.data.repository

import com.example.secureprogramming.feature.authentication.data.mapper.UserDataToDomainMapper
import com.example.secureprogramming.feature.authentication.data.mapper.UserSignInRequestDomainToDataMapper
import com.example.secureprogramming.feature.authentication.data.mapper.UserSignUpRequestDomainToDataMapper
import com.example.secureprogramming.feature.authentication.data.repository.datasource.AuthenticationSource
import com.example.secureprogramming.feature.authentication.domain.model.UserDomainModel
import com.example.secureprogramming.feature.authentication.domain.model.UserSignInRequestDomainModel
import com.example.secureprogramming.feature.authentication.domain.model.UserSignUpRequestDomainModel
import com.example.secureprogramming.feature.authentication.domain.repository.AuthenticationRepository
import javax.crypto.SecretKey
import javax.inject.Inject

class AuthenticationDataRepository @Inject constructor(
    private val authenticationSource: AuthenticationSource,
    private val userSignInRequestDomainToDataMapper: UserSignInRequestDomainToDataMapper,
    private val userSignUpRequestDomainToDataMapper: UserSignUpRequestDomainToDataMapper,
    private val userModelDataToDomainMapper: UserDataToDomainMapper
) : AuthenticationRepository {
    override suspend fun signIn(data: UserSignInRequestDomainModel): Result<UserDomainModel> {
        return userModelDataToDomainMapper.mapDomainToData(
            authenticationSource.signIn(
                userSignInRequestDomainToDataMapper.mapDomainToData(data)
            )
        )
    }

    override suspend fun signUp(data: UserSignUpRequestDomainModel): Result<UserDomainModel> {
        return userModelDataToDomainMapper.mapDomainToData(
            authenticationSource.signUp(
                userSignUpRequestDomainToDataMapper.mapDomainToData(data)
            )
        )
    }

    override suspend fun logout() {
        authenticationSource.logout()
    }

    override fun generateEncryptionKey(password: String) {
       return authenticationSource.generateEncryptionKey(password)
    }
}