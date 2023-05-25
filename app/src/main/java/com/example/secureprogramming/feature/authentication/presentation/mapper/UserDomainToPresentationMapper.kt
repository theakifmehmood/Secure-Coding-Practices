package com.example.secureprogramming.feature.authentication.presentation.mapper

import com.example.secureprogramming.feature.authentication.domain.model.UserDomainModel
import com.example.secureprogramming.feature.authentication.presentation.model.UserPresentationModel

class UserDomainToPresentationMapper {
    fun mapDomainToPresentation(user: UserDomainModel): UserPresentationModel {
        return  UserPresentationModel(
            id = user.id,
            name = user.name,
            email = user.email
        )
    }
}
