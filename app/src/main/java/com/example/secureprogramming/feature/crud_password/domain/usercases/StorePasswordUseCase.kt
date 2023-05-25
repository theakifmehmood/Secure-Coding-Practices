package com.example.secureprogramming.feature.crud_password.domain.usercases

import com.example.secureprogramming.feature.crud_password.domain.model.PasswordDomainModel
import com.example.secureprogramming.feature.crud_password.domain.repository.AddPasswordRepository


interface StorePasswordUseCase {
    suspend fun storePassword(data: PasswordDomainModel): Result<PasswordDomainModel>
}

class StorePasswordUseCaseImp(private val addPasswordRepository: AddPasswordRepository) :
    StorePasswordUseCase {

    override suspend fun storePassword(data: PasswordDomainModel): Result<PasswordDomainModel> {
        return addPasswordRepository.storePassword(data = data)
    }

}

