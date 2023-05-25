package com.example.secureprogramming.feature.crud_password.domain.usercases

import com.example.secureprogramming.feature.crud_password.domain.model.PasswordDomainModel
import com.example.secureprogramming.feature.crud_password.domain.repository.AddPasswordRepository


interface RetrievePasswordUseCase {
    suspend fun retrievePassword(): Result<List<PasswordDomainModel>>
}

class RetrievePasswordUseCaseImp(private val addPasswordRepository: AddPasswordRepository) :
    RetrievePasswordUseCase {

    override suspend fun retrievePassword(): Result<List<PasswordDomainModel>> {
        return addPasswordRepository.retrievePassword()
    }

}

