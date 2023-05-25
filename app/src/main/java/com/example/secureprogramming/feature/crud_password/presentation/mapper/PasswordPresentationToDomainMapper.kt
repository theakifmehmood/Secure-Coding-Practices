package com.example.secureprogramming.feature.crud_password.presentation.mapper

import com.example.secureprogramming.feature.crud_password.domain.model.PasswordDomainModel
import com.example.secureprogramming.feature.crud_password.presentation.model.PasswordPresentationModel

class PasswordPresentationToDomainMapper {
    fun mapDomainToData(data: PasswordPresentationModel): PasswordDomainModel {
        return PasswordDomainModel(
            id = data.id,
            title = data.title,
            userName = data.userName,
            password = data.password,
            link = data.link,
            note = data.note,
            userId = data.userId,
        )
    }
}
