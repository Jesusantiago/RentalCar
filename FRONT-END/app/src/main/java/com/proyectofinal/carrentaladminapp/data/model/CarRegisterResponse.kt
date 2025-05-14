package com.proyectofinal.carrentaladminapp.data.model

data class CarRegisterResponse(
    private val brand: String,
    private val model: String,
    private val carYear: Int,
    private val licensePlate: String,
    private val statusCar: String,
    private val branch: Long
)