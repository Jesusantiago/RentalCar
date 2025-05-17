package com.proyectofinal.carrentaladminapp.data.model

import com.proyectofinal.carrentaladminapp.data.StatusCar

data class Car(
    val id: Long,
    val brand: String,
    val model: String,
    val branchCity: String,
    val branchName: String,
    val statusCar: StatusCar
)
