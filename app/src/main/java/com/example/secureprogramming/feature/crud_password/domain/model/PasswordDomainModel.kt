package com.example.secureprogramming.feature.crud_password.domain.model

data class PasswordDomainModel(
    val id: String = "",
    val title: String = "",
    val userName: String = "",
    val password: String = "",
    val link: String = "",
    val note: String = "",
    val userId: String = "",
)