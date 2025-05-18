package com.proyectofinal.carrentaladminapp.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

import androidx.navigation.compose.rememberNavController
import com.proyectofinal.carrentaladminapp.R
import com.proyectofinal.carrentaladminapp.ui.cars.CarDetailScreen
import com.proyectofinal.carrentaladminapp.ui.cars.NewCarScreen
import com.proyectofinal.carrentaladminapp.ui.cars.UpdateCarScreen
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminMainScreen(){
    val navController = rememberNavController()
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 40.dp, bottom = 0.dp)
                        .height(150.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.store_profile_back),
                        contentDescription = "User Icon",
                        modifier = Modifier
                            .size(64.dp)
                    )
                    Text(
                        text= "Admin Name",
                        style = MaterialTheme.typography.titleLarge
                    )
                    Text("Ciudad", style = MaterialTheme.typography.bodySmall)

                    Spacer(modifier = Modifier.height(12.dp))

                    TextButton(
                        onClick = {/*TODO */}
                    ) {
                        Text(text = "Ver información")
                    }
                }

                NavigationDrawerItem(
                    label = { Text("Ver todos los autos") },
                    selected = false,
                    onClick = { navController.navigate("home") }
                )
                NavigationDrawerItem(
                    label = { Text("Ver todas las sucursales") },
                    selected = false,
                    onClick = { /* Ir a inicio */ }
                )
                NavigationDrawerItem(
                    label = { Text("Ver todas las rentas") },
                    selected = false,
                    onClick = { /* Ir a inicio */ }
                )

                Spacer(modifier = Modifier.weight(1f))
                NavigationDrawerItem(
                    label = { Text("Cerrar sesión", color= Color.White) },
                    selected = false,
                    onClick = { navController.navigate("welcome") },
                    modifier = Modifier.background(Color.Red)
                )
            }
        }
    ) {
        Scaffold(
            topBar = {
                AdminTopBar {
                    scope.launch { drawerState.open()
                    }
                }
            },
            floatingActionButton = {
                FloatingActionButton(onClick = { navController.navigate("newcar") }) {
                    Icon(Icons.Default.Add, contentDescription = "Add cars")
                }
            }
        ) { paddingValues ->

            NavHost(
                navController = navController,
                startDestination = "home",
                modifier = Modifier.fillMaxSize()
            ) {
                composable("home") { HomeAdminScreen(navController,paddingValues)}
                composable("newcar") { NewCarScreen(navController) }
                composable("carDetail/{carId}") { backStackEntry ->
                    val carId = backStackEntry.arguments?.getString("carId")?.toLong() ?: -1L
                    CarDetailScreen(carId = carId, navController = navController, paddingValues)
                }
                composable("updateCar/{carId}") { backStackEntry ->
                    val carId = backStackEntry.arguments?.getString("carId")?.toLong() ?: -1L
                    UpdateCarScreen(carId = carId, navController = navController, paddingValues)
                }

            }

        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminTopBar(onMenuClick: () -> Unit) {
    TopAppBar(
        title = { Text("RentalCars Admin") },
        navigationIcon = {
            IconButton(onClick = onMenuClick) {
                Icon(Icons.Default.Menu, contentDescription = "Open menu")
            }
        }
    )
}