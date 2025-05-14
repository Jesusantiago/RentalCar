package com.proyectofinal.carrentaladminapp.data.remote

import com.proyectofinal.carrentaladminapp.data.model.LoginRequest
import com.proyectofinal.carrentaladminapp.data.model.LoginResponse
import com.proyectofinal.carrentaladminapp.data.model.RegisterRequest
import com.proyectofinal.carrentaladminapp.data.model.RegisterResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface AuthService {
    @POST("/api/users/register")
    suspend fun register(@Body request: RegisterRequest): Response<RegisterResponse>

    @POST("api/auth/login")
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>
}
