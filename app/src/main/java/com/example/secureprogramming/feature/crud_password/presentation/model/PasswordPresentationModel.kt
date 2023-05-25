package com.example.secureprogramming.feature.crud_password.presentation.model

import java.io.Serializable

data class PasswordPresentationModel(
    val id: String = "",
    val title: String = "",
    val userName: String = "",
    val password: String = "",
    val link: String = "",
    val note: String = "",
    val userId: String = "",
): Serializable