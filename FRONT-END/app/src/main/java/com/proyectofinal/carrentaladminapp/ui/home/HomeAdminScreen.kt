@file:OptIn(ExperimentalMaterial3Api::class)

package com.proyectofinal.carrentaladminapp.ui.home
import android.annotation.SuppressLint
import androidx.activity.ComponentActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
fun HomeAdminScreen(navController: NavController, paddingValues: PaddingValues) {
    val viewModel: HomeViewModel = viewModel(LocalContext.current as ComponentActivity)
    val cars by viewModel.cars.collectAsState()


    val searchQueryState = rememberTextFieldState()
    var filteredCars by rememberSaveable { mutableStateOf(cars) }
    var query by rememberSaveable { mutableStateOf("") }

    LaunchedEffect(cars) {
        filteredCars = cars
        println("Esto es filteredcar: ${filteredCars}")
    }

    LaunchedEffect(Unit) {
        viewModel.getCars()
        println("Lista de autos actualizada, total: ${cars.size}")
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                start = paddingValues.calculateStartPadding(LayoutDirection.Ltr),
                end = paddingValues.calculateEndPadding(LayoutDirection.Ltr),
                bottom = paddingValues.calculateBottomPadding())
    )
    {

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
                CarPreviewScreen(car, viewModel, navController)
                Spacer(modifier = Modifier.height(12.dp))
            }
        }
    }
}



//
//
//@Preview
//@Composable
//fun HomeAdminScreenPreview(){
//    val navController = rememberNavController()
//    HomeAdminScreen(navController)
//}

//        Spacer(modifier = Modifier.height(56.dp))
