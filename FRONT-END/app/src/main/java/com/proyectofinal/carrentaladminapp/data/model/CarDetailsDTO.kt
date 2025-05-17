package com.proyectofinal.carrentaladminapp.data.model

data class CarDetailsDTO(
    val id : Long,
    val brand : String,
    val model : String,
    val carYear : Int,
    val licensePlate : CarBranchDetails
)

data class CarBranchDetails(
    val id: Long,
    val name: String,
    val address: String,
    val city: String,
    val phone: String
)