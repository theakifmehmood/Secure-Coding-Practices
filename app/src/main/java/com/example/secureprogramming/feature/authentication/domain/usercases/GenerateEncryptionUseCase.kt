package com.example.secureprogramming.feature.authentication.domain.usercases

import com.example.secureprogramming.feature.authentication.domain.repository.AuthenticationRepository

interface GenerateEncryptionUseCase {
    suspend fun generateEncryptionKey(password: String)
}

class GenerateEncryptionUseCaseImpl(private val userRepository: AuthenticationRepository) : GenerateEncryptionUseCase {
    override suspend fun generateEncryptionKey(password: String)  {
        return userRepository.generateEncryptionKey(password = password)
    }


}