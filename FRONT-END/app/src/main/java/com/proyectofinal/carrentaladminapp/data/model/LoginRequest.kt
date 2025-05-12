package com.proyectofinal.carrentaladminapp.data.model

data class LoginRequest(
    val email: String,
    val password: String
)

data class LoginResponse(
    val token: String,
    val idUser: Long,
    val userName: String,
    val licenseType: String
)
