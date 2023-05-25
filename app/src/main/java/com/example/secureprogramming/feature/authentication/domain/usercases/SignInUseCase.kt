package com.example.secureprogramming.feature.authentication.domain.usercases

import com.example.secureprogramming.feature.authentication.domain.model.UserDomainModel
import com.example.secureprogramming.feature.authentication.domain.model.UserSignInRequestDomainModel
import com.example.secureprogramming.feature.authentication.domain.repository.AuthenticationRepository


interface SignInUseCase {
    suspend fun signIn(userSignInRequestDomainModel: UserSignInRequestDomainModel): Result<UserDomainModel>
}

class SignInUseCaseImpl(private val userRepository: AuthenticationRepository) : SignInUseCase {
    override suspend fun signIn(userSignInRequestDomainModel: UserSignInRequestDomainModel): Result<UserDomainModel> {
        return userRepository.signIn(userSignInRequestDomainModel)
    }


}