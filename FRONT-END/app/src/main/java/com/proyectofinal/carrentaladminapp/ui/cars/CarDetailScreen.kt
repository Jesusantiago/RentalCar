package com.proyectofinal.carrentaladminapp.ui.cars

import android.annotation.SuppressLint
import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.proyectofinal.carrentaladminapp.ui.home.HomeViewModel

@SuppressLint("ContextCastToActivity")
@Composable
fun CarDetailScreen(carId: Long, navController: NavHostController) {
    val viewModel: HomeViewModel = viewModel(LocalContext.current as ComponentActivity)
    val carDetail = viewModel.carDetailsState
    val error = viewModel.errorState

    LaunchedEffect(carDetail) {
        viewModel.getACar(carId)
    }



    when {
//        isLoading -> {
//            CircularProgressIndicator()
//        }
        error != null -> {
            Text("Error cargando el auto: $error")
        }
        carDetail != null -> {
            // Mostrás los detalles del auto
            Column {
                Text("Modelo: ${carDetail!!.model}")
                Text("Marca: ${carDetail!!.brand}")
                // Agregá más campos...

                Button(onClick = {
                    navController.popBackStack()
                }) {
                    Text("Volver")
                }
            }
        }
    }
}