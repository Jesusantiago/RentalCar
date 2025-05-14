@file:OptIn(ExperimentalMaterial3Api::class)

package com.proyectofinal.carrentaladminapp.ui.home
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeAdminScreen(navController: NavController) {
    val viewModel: HomeViewModel = viewModel()
    val cars by viewModel.cars.collectAsState()



    Scaffold(
        topBar = { AdminTopBar() },
        floatingActionButton = {
            FloatingActionButton(onClick = { /* acción para agregar auto */ }) {
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
            IconButton(onClick = { /* acción de logout si querés */ }) {
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