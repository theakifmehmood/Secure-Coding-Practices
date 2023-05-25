package com.example.secureprogramming.feature.authentication.domain.model

data class UserSignUpRequestDomainModel(
    val email: String = "",
    val password: String = "",
    val name: String = "",
)