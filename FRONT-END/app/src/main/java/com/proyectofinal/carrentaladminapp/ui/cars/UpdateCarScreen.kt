package com.proyectofinal.carrentaladminapp.ui.cars

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.proyectofinal.carrentaladminapp.R
import com.proyectofinal.carrentaladminapp.data.model.UpdateCarDTO
import com.proyectofinal.carrentaladminapp.ui.home.HomeViewModel

@SuppressLint("ContextCastToActivity")
@Composable
fun UpdateCarScreen(carId: Long, navController: NavHostController, paddingValues: PaddingValues) {
    val viewModel: HomeViewModel = viewModel(LocalContext.current as ComponentActivity)
    val carDetail by remember { viewModel::carDetailsState }
    val error by remember { viewModel::errorState }

    var brand by remember { mutableStateOf(carDetail!!.brand) }
    var model by remember { mutableStateOf(carDetail!!.model) }
    var carYear by remember { mutableIntStateOf(carDetail!!.carYear) }
    var licensePlate by remember { mutableStateOf(carDetail!!.licensePlate) }

    LaunchedEffect(carId) {
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
            LazyColumn(
                modifier = Modifier
                    .padding(
                        start = 16.dp,
                        end = 16.dp,
                        top = paddingValues.calculateTopPadding(),
                        bottom = paddingValues.calculateBottomPadding())
            ) {
                item {
                    Image(
                        painter = painterResource(id = R.drawable.car_detail),
                        contentDescription = "User Icon",
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                item {
                    Text("Datos del Auto:", fontWeight = FontWeight.Bold, fontSize = 20.sp)
                }

                item {
                    Spacer(modifier = Modifier.height(20.dp))
                }

                item {
                    OutlinedTextField(
                        value = brand,
                        onValueChange = { brand = it },
                        singleLine = true,
                        label = { Text("Marca") },
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                item {
                    OutlinedTextField(
                        value = model,
                        onValueChange = { model = it },
                        singleLine = true,
                        label = { Text("Modelo") },
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                item {
                    OutlinedTextField(
                        value = carYear.toString(),
                        onValueChange = {
                            carYear = it.toIntOrNull() ?: 0
                        },
                        singleLine = true,
                        label = { Text("Año del auto") },
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                item {
                    OutlinedTextField(
                        value = licensePlate,
                        onValueChange = {licensePlate = it},
                        singleLine = true,
                        label = { Text("Patente del auto") },
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                item {
                    Spacer(modifier = Modifier.height(16.dp))
                }

                item {
                    Row(
                        modifier = Modifier
                            .padding(top = 16.dp)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.Absolute.SpaceAround
                    ) {
                        Button(
                            onClick = {navController.popBackStack()},
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.error ,
                                contentColor = Color.White
                            )
                        ) {
                            Text("Cancelar")
                        }

                        Button(
                            onClick = {
                                viewModel.updateACar(carDetail!!.id,
                                    UpdateCarDTO(brand, model, carYear, licensePlate)
                                )

                                navController.navigate("carDetail/${carDetail!!.id}")
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFF4CAF50),
                                contentColor = Color.White
                            )
                        ) {
                            Text("Guardar")
                        }
                    }
                }
            }

        }
    }
}