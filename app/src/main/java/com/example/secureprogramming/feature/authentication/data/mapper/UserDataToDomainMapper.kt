package com.example.secureprogramming.feature.authentication.data.mapper


import com.example.secureprogramming.feature.authentication.data.model.UserDataModel
import com.example.secureprogramming.feature.authentication.domain.model.UserDomainModel

class UserDataToDomainMapper {
    fun mapDomainToData(user: Result<UserDataModel>): Result<UserDomainModel> {
        return if (user.isSuccess) {
            val userData = user.getOrThrow()
            Result.success(
                UserDomainModel(
                    id = userData.id,
                    name = userData.name,
                    email = userData.email
                )
            )
        } else {
            Result.failure(user.exceptionOrNull() ?: Exception("Unknown error occurred."))
        }
    }
}
