package com.example.secureprogramming.feature.home.presentation.mapper

import com.example.secureprogramming.feature.crud_password.domain.model.PasswordDomainModel
import com.example.secureprogramming.feature.crud_password.presentation.model.PasswordPresentationModel

class PasswordDomainToPresentationListMapper {
    fun map(passwordDataList: List<PasswordDomainModel>): List<PasswordPresentationModel> {
        return passwordDataList.map {
            PasswordPresentationModel(
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