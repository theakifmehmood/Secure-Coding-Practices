package com.example.secureprogramming.feature.crud_password.data.repository.datasource


import com.example.secureprogramming.feature.crud_password.data.model.PasswordDataModel

interface AddPasswordSource {

    suspend fun storePassword(data : PasswordDataModel) : Result<PasswordDataModel>

    suspend fun retrievePassword() : Result<List<PasswordDataModel>>

    suspend fun updatePassword(data : PasswordDataModel) : Result<PasswordDataModel>

    suspend fun deletePassword() : Result<Pair<String,Boolean>>



}