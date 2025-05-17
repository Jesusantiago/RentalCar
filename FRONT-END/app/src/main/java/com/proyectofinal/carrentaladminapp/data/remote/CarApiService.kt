package com.proyectofinal.carrentaladminapp.data.remote

import com.proyectofinal.carrentaladminapp.data.model.BranchNewCar
import com.proyectofinal.carrentaladminapp.data.model.Car
import com.proyectofinal.carrentaladminapp.data.model.CarDetailsDTO
import com.proyectofinal.carrentaladminapp.data.model.CarRegisterResponse
import com.proyectofinal.carrentaladminapp.data.model.PageResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface CarApiService {
    @GET("/admin/search")
    suspend fun getCars(
        @Query("page") page: Int = 0,
        @Query("size") size: Int = 10,
        @Query("sortBy") sortBy: String = "brand",
        @Query("direction") direction: String = "asc",
    ): PageResponse<Car>

    @GET("/admin/search")
    suspend fun getCarsByBrand(
        @Query("page") page: Int = 0,
        @Query("size") size: Int = 10,
        @Query("brand") brand: String,
    ): PageResponse<Car>

    @GET("/admin/car/{id}")
    suspend fun getACarById(
        @Path("id") id :Long
    ) : Response<CarDetailsDTO>

    @POST("/admin/newcar")
    suspend fun newCar(@Body response: CarRegisterResponse) : CarRegisterResponse

    @GET("/admin/branchs")
    suspend fun branchForNewCar(): List<BranchNewCar>

    @DELETE("/admin/car/{id}")
    suspend fun deleteCar(
        @Path("id") id: Long
    ) : Response<Void>
}