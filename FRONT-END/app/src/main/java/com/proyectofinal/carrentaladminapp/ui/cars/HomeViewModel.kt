package com.proyectofinal.carrentaladminapp.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.proyectofinal.carrentaladminapp.data.model.Car
import com.proyectofinal.carrentaladminapp.data.remote.RetrofitCar
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel(){
    private val _cars = MutableStateFlow<List<Car>>(emptyList())
    val cars: StateFlow<List<Car>> = _cars

    init {
        getCars()
    }

    private fun getCars(){
        viewModelScope.launch {
            try {
                val response = RetrofitCar.api.getCars()
                _cars.value = response.content

                // Print del objeto entero
                println("Respuesta completa: $response")

                // Si querés solo el contenido (lista de autos)
                println("Autos: ${response.content}")

                // O usando Log para verlo en Logcat
            } catch (e: Exception) {
                e.printStackTrace()
                println("Error en getCars: ${e.message}")

            }
        }
    }
}

