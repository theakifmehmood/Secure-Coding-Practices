package com.example.secureprogramming.feature.crud_password.data.mapper


import com.example.secureprogramming.feature.crud_password.data.model.PasswordDataModel
import com.example.secureprogramming.feature.crud_password.domain.model.PasswordDomainModel

class PasswordDataToDomainMapper {
    fun mapDomainToData(data: Result<PasswordDataModel>): Result<PasswordDomainModel> {
        return if (data.isSuccess) {
            val result = data.getOrThrow()
            Result.success(
                PasswordDomainModel(
                    id = result.id,
                    title = result.title,
                    userName = result.userName,
                    password = result.password,
                    link = result.link,
                    note = result.note,
                    userId = result.userId,
                )
            )
        } else {
            Result.failure(data.exceptionOrNull() ?: Exception("Unknown error occurred."))
        }
    }
}
