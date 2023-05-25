package com.example.secureprogramming.feature.authentication.data.model

data class UserSignUpRequestDataModel(
    val email: String= "",
    val password: String= "",
    val name: String = "",
)