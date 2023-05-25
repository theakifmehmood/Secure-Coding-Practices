package com.example.secureprogramming.feature.authentication.domain.usercases

import com.example.secureprogramming.feature.authentication.domain.repository.AuthenticationRepository

interface LogoutUseCase {
    suspend fun logout()
}

class LogoutUseCaseImpl(private val userRepository: AuthenticationRepository) : LogoutUseCase {
    override suspend fun logout() {
        return userRepository.logout()
    }


}