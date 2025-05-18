package com.proyectofinal.carrentaladminapp.data.remote

import com.proyectofinal.carrentaladminapp.data.model.BranchDTO
import com.proyectofinal.carrentaladminapp.data.model.BranchNewCar
import com.proyectofinal.carrentaladminapp.data.model.Car
import com.proyectofinal.carrentaladminapp.data.model.CarDetailsDTO
import com.proyectofinal.carrentaladminapp.data.model.CarRegisterResponse
import com.proyectofinal.carrentaladminapp.data.model.PageResponse
import com.proyectofinal.carrentaladminapp.data.model.UpdateCarDTO
import okhttp3.Request
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface CarApiService {

    @GET("/admin/search")
    suspend fun getCars(
        @Query("page") page: Int,
        @Query("size") size: Int,
        @Query("sortBy") sortBy: String = "brand",
        @Query("direction") direction: String = "asc",
    ): Response<PageResponse<Car>>

    @GET("/admin/search")
    suspend fun getCarsByBrand(
        @Query("page") page: Int,
        @Query("size") size: Int,
        @Query("brand") brand: String,
    ): PageResponse<Car>

    @GET("/admin/car/{id}")
    suspend fun getACarById(
        @Path("id") id :Long
    ) : Response<CarDetailsDTO>

    @POST("/admin/newcar")
    suspend fun newCar(@Body response: CarRegisterResponse) : CarRegisterResponse

    @GET("/admin/branchsCar")
    suspend fun branchForNewCar(): List<BranchNewCar>

    @PUT("/admin/car/{id}")
    suspend fun updateACar(
        @Path("id") id : Long,
        @Body request: UpdateCarDTO
    ) : Response<CarDetailsDTO>

    @DELETE("/admin/car/{id}")
    suspend fun deleteCar(
        @Path("id") id: Long
    ) : Response<Void>


    @GET("/admin/branch/{id}")
    suspend fun getBranchById(@Path("id") id: Long) : Response<BranchDTO>

    @GET("/admin/branches")
    suspend fun getAllBranches() : Response<List<BranchDTO>>

    @POST("/admin/branch")
    suspend fun createBranch(@Body branch: BranchDTO): Response<BranchDTO>

    @PUT("/admin/branches/{id}")
    suspend fun updateABranch(
        @Path("id") id: Long,
        @Body request: BranchDTO
    ) : Response<BranchDTO>

    @DELETE("/admin/branches/{id}")
    suspend fun deleteABranch(
        @Path("id") id: Long
    ) : Response<Void>




}