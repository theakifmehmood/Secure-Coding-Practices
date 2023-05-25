package com.example.secureprogramming.feature.crud_password.data.mapper

import com.example.secureprogramming.feature.crud_password.data.model.PasswordDataModel
import com.example.secureprogramming.feature.crud_password.domain.model.PasswordDomainModel

class PasswordDomainToDataMapper {
    fun mapDomainToData(data: PasswordDomainModel): PasswordDataModel {
        return PasswordDataModel(
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
