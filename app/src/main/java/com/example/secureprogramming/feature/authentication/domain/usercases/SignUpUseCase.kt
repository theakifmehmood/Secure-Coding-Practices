package com.example.secureprogramming.feature.authentication.domain.usercases

import com.example.secureprogramming.feature.authentication.domain.model.UserDomainModel
import com.example.secureprogramming.feature.authentication.domain.model.UserSignUpRequestDomainModel
import com.example.secureprogramming.feature.authentication.domain.repository.AuthenticationRepository


interface SignUpUseCase {
    suspend fun signUp(userSignUpRequestDomainModel: UserSignUpRequestDomainModel): Result<UserDomainModel>
}

class SignUpUseCaseImpl(private val authenticationRepository: AuthenticationRepository) : SignUpUseCase {
    override suspend fun signUp(userSignUpRequestDomainModel: UserSignUpRequestDomainModel): Result<UserDomainModel> {
        return authenticationRepository.signUp(userSignUpRequestDomainModel)
    }
}

