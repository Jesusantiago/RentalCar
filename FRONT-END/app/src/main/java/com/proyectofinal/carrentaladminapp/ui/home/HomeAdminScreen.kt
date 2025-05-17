@file:OptIn(ExperimentalMaterial3Api::class)

package com.proyectofinal.carrentaladminapp.ui.home
import android.annotation.SuppressLint
import androidx.activity.ComponentActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsTopHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.BeyondBoundsLayout
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.proyectofinal.carrentaladminapp.R
import com.proyectofinal.carrentaladminapp.data.remote.CarApiService
import com.proyectofinal.carrentaladminapp.data.remote.RetrofitCar
import com.proyectofinal.carrentaladminapp.ui.cars.CarPreviewScreen
import com.proyectofinal.carrentaladminapp.ui.components.FilterForBrand
import com.proyectofinal.carrentaladminapp.ui.components.SearchBarUI
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.selects.select
import kotlinx.coroutines.withContext

@SuppressLint("ContextCastToActivity")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeAdminScreen(navController: NavController) {
    val viewModel: HomeViewModel = viewModel(LocalContext.current as ComponentActivity)
    val cars by viewModel.cars.collectAsState()

    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    val searchQueryState = rememberTextFieldState()
    var filteredCars by rememberSaveable { mutableStateOf(cars) }
    var query by rememberSaveable { mutableStateOf("") }

    LaunchedEffect(cars) {
        filteredCars = cars
        print("Esto es filteredcar: ${filteredCars}")
    }

    LaunchedEffect(Unit) {
        viewModel.getCars()
        println("Lista de autos actualizada, total: ${cars.size}")
    }

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
                    onClick = { /* Ir a inicio */ }
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
        // <-- ¡El Scaffold va adentro de este bloque!
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
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(
                        start = paddingValues.calculateStartPadding(LayoutDirection.Ltr),
                        end = paddingValues.calculateEndPadding(LayoutDirection.Ltr),
                        bottom = paddingValues.calculateBottomPadding()
                    )
            ) {
                Spacer(modifier = Modifier.height(56.dp))

                SearchBarUI(
                    modifier = Modifier.fillMaxWidth(),
                    textFieldState = searchQueryState,
                    onSearch = {
                        query = it
                        filteredCars = cars.filter { car ->
                            car.brand.contains(it, ignoreCase = true) ||
                                    car.model.contains(it, ignoreCase = true)
                        }
                    },
                    searchResults = cars.map { "${it.brand} ${it.model}" },
                )
                Spacer(modifier = Modifier.height(16.dp))

                FilterForBrand(viewModel)



                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    items(filteredCars) { car ->
                        CarPreviewScreen(car)
                        Spacer(modifier = Modifier.height(12.dp))
                    }
                }
            }
        }
    }

}

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



@Preview
@Composable
fun HomeAdminScreenPreview(){
    val navController = rememberNavController()
    HomeAdminScreen(navController)
}

//
//Text("${car.brand} ${car.model}")
//IconButton(onClick = {
//    CoroutineScope(Dispatchers.IO).launch {
//        try {
//            val response = RetrofitCar.api.deleteCar(car.id.toLong())
//            if (response.isSuccessful()) {
//                withContext(Dispatchers.Main) {
//                    viewModel.getCars()
//                }
//            } else {
//                println("Error al borrar: ${response.code()}")
//            }
//        } catch (e: Exception) {
//            println("ERROR: ${e.message}")
//        }
//    }
//}) {
//    Icon(Icons.Default.Delete, contentDescription = "Delete Car", tint = Color.Red)
//}