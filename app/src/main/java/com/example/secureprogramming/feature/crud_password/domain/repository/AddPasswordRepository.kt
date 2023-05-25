package com.example.secureprogramming.feature.crud_password.domain.repository

import com.example.secureprogramming.feature.crud_password.domain.model.PasswordDomainModel

interface AddPasswordRepository {

    suspend fun storePassword(data : PasswordDomainModel) : Result<PasswordDomainModel>

    suspend fun retrievePassword() : Result<List<PasswordDomainModel>>

}