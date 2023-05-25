package com.example.secureprogramming.feature.crud_password.data.repository

import com.example.secureprogramming.feature.crud_password.data.mapper.PasswordDataToDomainListMapper
import com.example.secureprogramming.feature.crud_password.data.mapper.PasswordDataToDomainMapper
import com.example.secureprogramming.feature.crud_password.data.mapper.PasswordDomainToDataMapper
import com.example.secureprogramming.feature.crud_password.data.repository.datasource.AddPasswordSource
import com.example.secureprogramming.feature.crud_password.domain.model.PasswordDomainModel
import com.example.secureprogramming.feature.crud_password.domain.repository.AddPasswordRepository
import javax.inject.Inject

class AddPasswordDataRepository @Inject constructor(
    private val addPasswordSource: AddPasswordSource,
    private val passwordDomainToDataMapper: PasswordDomainToDataMapper,
    private val passwordDataToDomainMapper: PasswordDataToDomainMapper
) : AddPasswordRepository {

    override suspend fun storePassword(data: PasswordDomainModel): Result<PasswordDomainModel> {
      return  passwordDataToDomainMapper.mapDomainToData(addPasswordSource.storePassword(data = passwordDomainToDataMapper.mapDomainToData(data)))
    }

    override suspend fun retrievePassword(): Result<List<PasswordDomainModel>> {
        val passwordDataResult = addPasswordSource.retrievePassword()

        return if (passwordDataResult.isSuccess) {
            val passwordDataList = passwordDataResult.getOrNull()
            val passwordDomainList = PasswordDataToDomainListMapper.map(passwordDataList!!)
            Result.success(passwordDomainList)

        } else {
            Result.failure(passwordDataResult.exceptionOrNull()?: Exception("Unknown error"))
        }
    }

}