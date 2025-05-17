package com.proyectofinal.carrentaladminapp.ui.cars

import android.annotation.SuppressLint
import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.proyectofinal.carrentaladminapp.ui.home.HomeViewModel

@SuppressLint("ContextCastToActivity")
@Composable
fun CarDetailScreen(navController: NavHostController) {
    val viewModel: HomeViewModel = viewModel(LocalContext.current as ComponentActivity)
    val carDetail = viewModel.carDetailsState

    if (carDetail == null) {
        // Puedes mostrar un loading o un mensaje si aún no se cargó el auto
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("Cargando detalles del auto...")
        }
    } else {
        // Ya tenemos los datos del auto
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text("Modelo: ${carDetail.model}", fontSize = 24.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))

            Text("Marca: ${carDetail.brand}", fontSize = 20.sp)
            Spacer(modifier = Modifier.height(8.dp))

            Text("Año: ${carDetail.carYear}", fontSize = 20.sp)
            Spacer(modifier = Modifier.height(8.dp))

            Text("Precio por día: $${carDetail.licensePlate}", fontSize = 20.sp)
            Spacer(modifier = Modifier.height(8.dp))

            Button(onClick = {
                viewModel.clearCarDetails()
                navController.popBackStack() }
            ) {
                Text(text = "Volver")
            }
        }
    }

}