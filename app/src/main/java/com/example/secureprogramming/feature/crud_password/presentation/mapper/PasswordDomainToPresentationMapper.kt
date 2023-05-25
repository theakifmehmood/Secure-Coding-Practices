package com.example.secureprogramming.feature.crud_password.presentation.mapper
import com.example.secureprogramming.feature.crud_password.domain.model.PasswordDomainModel
import com.example.secureprogramming.feature.crud_password.presentation.model.PasswordPresentationModel

class PasswordDomainToPresentationMapper {
    fun mapDomainToData(data: Result<PasswordDomainModel>): Result<PasswordPresentationModel> {
        return if (data.isSuccess) {
            val result = data.getOrThrow()
            Result.success(
                PasswordPresentationModel(
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
