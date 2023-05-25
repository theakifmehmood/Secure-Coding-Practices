package com.example.secureprogramming.feature.authentication.domain.model

data class UserSignInRequestDomainModel(
    val email: String,
    val password: String
)