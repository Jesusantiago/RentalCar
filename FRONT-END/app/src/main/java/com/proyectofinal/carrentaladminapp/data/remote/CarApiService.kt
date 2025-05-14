package com.proyectofinal.carrentaladminapp.data.remote

import com.proyectofinal.carrentaladminapp.data.model.Car
import com.proyectofinal.carrentaladminapp.data.model.PageResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface CarApiService {
    @GET("/admin/search")
    suspend fun getCars(
        @Query("page") page: Int = 0,
        @Query("size") size: Int = 10,
        @Query("sortBy") sortBy: String = "brand",
        @Query("direction") direction: String = "asc",
        @Query("status") status: String="AVAILABLE"
    ): PageResponse<Car>
}