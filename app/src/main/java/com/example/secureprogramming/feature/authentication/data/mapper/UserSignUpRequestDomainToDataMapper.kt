package com.example.secureprogramming.feature.authentication.data.mapper


import com.example.secureprogramming.feature.authentication.data.model.UserSignUpRequestDataModel
import com.example.secureprogramming.feature.authentication.domain.model.UserSignUpRequestDomainModel

class UserSignUpRequestDomainToDataMapper {
    fun mapDomainToData(user: UserSignUpRequestDomainModel): UserSignUpRequestDataModel {
        return UserSignUpRequestDataModel(
            name = user.name,
            email = user.email,
            password = user.password
        )
    }
}
