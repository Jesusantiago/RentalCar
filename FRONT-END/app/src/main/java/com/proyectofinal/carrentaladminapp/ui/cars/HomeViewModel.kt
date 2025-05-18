package com.proyectofinal.carrentaladminapp.ui.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.proyectofinal.carrentaladminapp.data.model.Car
import com.proyectofinal.carrentaladminapp.data.model.CarDetailsDTO
import com.proyectofinal.carrentaladminapp.data.model.UpdateCarDTO
import com.proyectofinal.carrentaladminapp.data.remote.RetrofitCar
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel(){
    private val _cars = MutableStateFlow<List<Car>>(emptyList())
    var carDetailsState by mutableStateOf<CarDetailsDTO?>(null)
        private set
    var errorState by mutableStateOf<String?>(null)
    private set

    val cars: StateFlow<List<Car>> = _cars

    init {
        getCars()
    }

    fun getCars(){
        viewModelScope.launch {
            try {
                val response = RetrofitCar.api.getCars()
                _cars.value = response.content
                println(response.content)

            } catch (e: Exception) {
                e.printStackTrace()
                println("Error en getCars: ${e.message}")

            }
        }
    }

    fun removeCarFromList(id: Long) {
        _cars.value = _cars.value.filter { it.id != id }
    }

    fun getCarsByBrand(brand: String) {
        viewModelScope.launch {
            try {
                val response = RetrofitCar.api.getCarsByBrand(brand = brand)
                _cars.value = response.content // o como lo manejes
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
}

