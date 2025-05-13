package com.proyectofinal.carrentaladminapp.data.model

data class RegisterRequest(
    val userName: String,
    val name: String,
    val lastName: String,
    val email: String,
    val password: String,
    val licenseType: String
)

data class RegisterResponse(
    val id: Long,
    val name: String,
    val lastName: String,
    val email: String,
    val token: String
)