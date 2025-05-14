package com.proyectofinal.carrentaladminapp.data.model

data class PageResponse<T>(
    val content: List<T>,
    val totalPages: Int,
    val totalElements: Int,
    val size: Int,
    val number: Int
)
