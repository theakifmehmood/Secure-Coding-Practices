package com.example.secureprogramming.feature.authentication.data.mapper


import com.example.secureprogramming.feature.authentication.data.model.UserSignInRequestDataModel
import com.example.secureprogramming.feature.authentication.domain.model.UserSignInRequestDomainModel

class UserSignInRequestDomainToDataMapper {
    fun mapDomainToData(user: UserSignInRequestDomainModel): UserSignInRequestDataModel {
        return UserSignInRequestDataModel(
            email = user.email,
            password = user.password
        )
    }
}

