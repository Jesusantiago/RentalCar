@file:OptIn(ExperimentalMaterial3Api::class)

package com.proyectofinal.carrentaladminapp.ui.home
import android.annotation.SuppressLint
import androidx.activity.ComponentActivity
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.proyectofinal.carrentaladminapp.data.remote.CarApiService
import com.proyectofinal.carrentaladminapp.data.remote.RetrofitCar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@SuppressLint("ContextCastToActivity")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeAdminScreen(navController: NavController) {
    val viewModel: HomeViewModel = viewModel(LocalContext.current as ComponentActivity)
    val cars by viewModel.cars.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.getCars()
        println("Lista de autos actualizada, total: ${cars.size}")
    }



    Scaffold(
        topBar = { AdminTopBar() },
        floatingActionButton = {
            FloatingActionButton(onClick = { navController.navigate("newcar") }) {
                Icon(Icons.Default.Add, contentDescription = "Add cars")
            }
        }
    ) { paddingValues ->  // Recibimos paddingValues correctamente
        LazyColumn(
            contentPadding = paddingValues,  // Usamos paddingValues aquí
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp) // Agregamos un margen adicional al contenido
        ) {
            items(cars) { car ->
                    Text("${car.brand} ${car.model}") // Aquí luego iría la carta con mejor diseño
                    IconButton(onClick = {
                        CoroutineScope(Dispatchers.IO).launch {
                            try{
                                var response = RetrofitCar.api.deleteCar(car.id.toLong())

                                if (response.isSuccessful()){
                                    withContext(Dispatchers.Main) {
                                        viewModel.getCars()
                                    }
                                } else {
                                    println("Error al borrar: ${response.code()}")
                                }

                            }catch (e: Exception){
                                println("ERROR: ${e.message}")
                            }
                        }

                    }){
                        Icon(Icons.Default.Delete, contentDescription = "Delete Car", tint = Color.Red)
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                }

//                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }


@Composable
fun AdminTopBar() {

    TopAppBar(
        title = { Text("RentalCars Admin") },
        actions = {
            IconButton(onClick = { /* */ }) {
                Icon(Icons.Default.ExitToApp, contentDescription = "Logout")
            }
        }
    )
}

@Preview
@Composable
fun HomeAdminScreenPreview(){
    val navController = rememberNavController()
    HomeAdminScreen(navController)
}