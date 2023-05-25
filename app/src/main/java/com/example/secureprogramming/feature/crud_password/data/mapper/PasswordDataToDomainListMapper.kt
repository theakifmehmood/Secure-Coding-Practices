package com.example.secureprogramming.feature.crud_password.data.mapper

import com.example.secureprogramming.feature.crud_password.data.model.PasswordDataModel
import com.example.secureprogramming.feature.crud_password.domain.model.PasswordDomainModel

object PasswordDataToDomainListMapper {
    fun map(passwordDataList: List<PasswordDataModel>): List<PasswordDomainModel> {
        return passwordDataList.map {
            PasswordDomainModel(
                id = it.id,
                title = it.title,
                userName = it.userName,
                password = it.password,
                link = it.link,
                note = it.note,
                userId = it.userId,
            )
        }
    }
}