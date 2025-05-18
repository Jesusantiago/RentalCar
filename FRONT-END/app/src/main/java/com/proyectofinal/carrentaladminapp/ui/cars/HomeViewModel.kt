package com.proyectofinal.carrentaladminapp.ui.home

import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.proyectofinal.carrentaladminapp.data.model.BranchDTO
import com.proyectofinal.carrentaladminapp.data.model.Car
import com.proyectofinal.carrentaladminapp.data.model.CarDetailsDTO
import com.proyectofinal.carrentaladminapp.data.model.UpdateCarDTO
import com.proyectofinal.carrentaladminapp.data.remote.RetrofitCar
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel(){
    private val _branch = mutableStateOf< BranchDTO?>(null)
    val branch: State<BranchDTO?> = _branch

    private val _cars = mutableStateOf(listOf<Car>())
    val cars: State<List<Car>> = _cars

    var currentPage by mutableStateOf(0)
    var totalPages by mutableStateOf(1)

    var currentBrand by mutableStateOf<String?>(null)
    var currentBrandPage by mutableStateOf(0)
    var totalBrandPages by mutableStateOf(1)

    var carDetailsState by mutableStateOf<CarDetailsDTO?>(null)
        private set
    var errorState by mutableStateOf<String?>(null)
        private set

    init {
        getCars()
    }

    fun resetAndFetchCars() {
        currentBrand = null
        currentPage = 0
        totalPages = 1
        getCars(0)
    }

    fun getCars(page: Int = 0) {
        viewModelScope.launch {
            try {
                val response = RetrofitCar.api.getCars(page, 10)

                if (response.isSuccessful) {
                    val body = response.body()
                    _cars.value = body?.content ?: emptyList()
                    currentPage = body?.number ?: 0
                    totalPages = body?.totalPages ?: 1
                } else {
                    errorState = "Error al obtener autos: ${response.code()}"
                }

            } catch (e: Exception) {
                e.printStackTrace()
                errorState = "Excepción en getCars: ${e.message}"
            }
        }
    }

    fun getCarsByBrand(brand: String, page: Int = 0) {
        viewModelScope.launch {
            try {
                val response = RetrofitCar.api.getCarsByBrand(page = page, size = 10, brand = brand)
                _cars.value = response.content
                currentBrand = brand
                currentBrandPage = response.number
                totalBrandPages = response.totalPages
            } catch (e: Exception) {
                println("Error: ${e.message}")
            }
        }
    }

    fun getACar(id: Long){
        viewModelScope.launch {
            try {
                val response = RetrofitCar.api.getACarById(id = id)
                if (response.isSuccessful){
                    carDetailsState = response.body()
                    println("ACAAAAAA" + response.body())
                    errorState = null
                } else {
                    errorState = "Hubo un problema para encontrar el auto"
                }
            } catch (e : Exception){
                print("Error: ${e.message}")
            }
        }
    }

    fun updateACar(id: Long, dateForUpdate : UpdateCarDTO){
        viewModelScope.launch {
            try {
                val response = RetrofitCar.api.updateACar(id = id, request = dateForUpdate)

                if (response.isSuccessful) {
                    response.body()?.let {
                        carDetailsState = it
                    } ?: run {
                        println("Respuesta exitosa pero sin cuerpo")
                    }
                } else {
                    println("Fallo la actualización: ${response.code()} ${response.message()}")
                }

            } catch (e : Exception){
                println("Error ${e.message}")
            }
        }
    }

    fun deleteCar(id : Long){
        viewModelScope.launch{
            try {
                var response = RetrofitCar.api.deleteCar(id = id)

                if (response.code() != 204){
                    println("Hubo un error al eliminar este auto")
                }
            } catch (e: Exception){
                println("Error: ${e.message}")
            }
        }
    }

    fun getBranch(branchId : Long){
        viewModelScope.launch {
            try {
                val response = RetrofitCar.api.getBranchById(branchId)
                if (response.isSuccessful) {
                    _branch.value = response.body()
                } else {
                    println("Error al obtener la branch: ${response.code()}")
                }
            } catch (e: Exception) {
                println("Excepción al obtener la branch: ${e.message}")
            }
        }
    }
}
