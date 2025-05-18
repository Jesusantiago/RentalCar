package com.proyectofinal.carrentaladminapp.ui.home

import android.annotation.SuppressLint
import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController

import com.proyectofinal.carrentaladminapp.ui.cars.CarPreviewScreen
import com.proyectofinal.carrentaladminapp.ui.components.FilterForBrand
import com.proyectofinal.carrentaladminapp.ui.components.SearchBarUI

@SuppressLint("ContextCastToActivity")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeAdminScreen(navController: NavController, paddingValues: PaddingValues, refresh: Boolean = false) {
    val viewModel: HomeViewModel = viewModel(LocalContext.current as ComponentActivity)
    val cars = viewModel.cars.value
    val isFilteringByBrand = viewModel.currentBrand != null

    val currentPage = if (isFilteringByBrand) viewModel.currentBrandPage else viewModel.currentPage
    val totalPages = if (isFilteringByBrand) viewModel.totalBrandPages else viewModel.totalPages

    val searchQueryState = rememberTextFieldState()
    var filteredCars by rememberSaveable { mutableStateOf(cars) }
    var query by rememberSaveable { mutableStateOf("") }
    var isFiltering by rememberSaveable { mutableStateOf(false) }
    var hasRefreshed by remember { mutableStateOf(false) }

    LaunchedEffect(refresh) {
        if (refresh && !hasRefreshed) {
            viewModel.resetAndFetchCars()
            hasRefreshed = true
        }
    }


    LaunchedEffect(cars) {
        filteredCars = cars
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                start = paddingValues.calculateStartPadding(LayoutDirection.Ltr),
                end = paddingValues.calculateEndPadding(LayoutDirection.Ltr),
                bottom = paddingValues.calculateBottomPadding()
            )
    ) {

        SearchBarUI(
            modifier = Modifier.fillMaxWidth(),
            textFieldState = searchQueryState,
            onSearch = {
                query = it
                isFiltering = it.isNotBlank()
                if (isFiltering) {
                    viewModel.getCarsByBrand(it, 0)
                } else {
                    viewModel.currentBrand = null // ⬅️ Limpia la marca activa si no hay búsqueda
                    viewModel.getCars(0)
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

            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Button(
                        onClick = {
                            if (currentPage > 0) {
                                if (isFilteringByBrand) {
                                    viewModel.getCarsByBrand(viewModel.currentBrand!!, currentPage - 1)
                                } else {
                                    viewModel.getCars(currentPage - 1)
                                }
                            }
                        },
                        enabled = currentPage > 0
                    ) {
                        Text("Anterior")
                    }

                    Text("Página ${currentPage + 1} de $totalPages")

                    Button(
                        onClick = {
                            if (currentPage < totalPages - 1) {
                                if (isFilteringByBrand) {
                                    viewModel.getCarsByBrand(viewModel.currentBrand!!, currentPage + 1)
                                } else {
                                    viewModel.getCars(currentPage + 1)
                                }
                            }
                        },
                        enabled = currentPage < totalPages - 1
                    ) {
                        Text("Siguiente")
                    }
                }
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
