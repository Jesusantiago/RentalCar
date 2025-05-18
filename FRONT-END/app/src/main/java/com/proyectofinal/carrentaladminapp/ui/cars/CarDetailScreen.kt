package com.proyectofinal.carrentaladminapp.ui.cars

import android.annotation.SuppressLint
import androidx.activity.ComponentActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontVariation
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.proyectofinal.carrentaladminapp.R
import com.proyectofinal.carrentaladminapp.ui.home.HomeViewModel

@SuppressLint("ContextCastToActivity")
@Composable
fun CarDetailScreen(carId: Long, navController: NavHostController, paddingValues: PaddingValues) {
    val viewModel: HomeViewModel = viewModel(LocalContext.current as ComponentActivity)
    val carDetail by remember { viewModel::carDetailsState }
    val error by remember { viewModel::errorState }

    println("CarDetail recomposed with 3: $carDetail")

    LaunchedEffect(carId) {
        println("CarDetail recomposed with: $carDetail")
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
            Column(
                modifier = Modifier
                    .padding(
                        start = 16.dp,
                        end = 16.dp,
                        top = paddingValues.calculateTopPadding(),
                        bottom = paddingValues.calculateBottomPadding())
            ) {
                Image(
                    painter = painterResource(id = R.drawable.car_detail),
                    contentDescription = "User Icon",
                    modifier = Modifier
                        .fillMaxWidth()
                )
                Text("Datos del Auto:", fontWeight = FontWeight.Bold, fontSize = 20.sp)

                Spacer(modifier = Modifier.height(20.dp))

                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()

                ){
                    Text("Marca:")
                    Text("${carDetail!!.brand}")
                }

                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()

                ){
                    Text("Modelo:")
                    Text("${carDetail!!.model}")
                }

                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()

                ){
                    Text("Año del auto:")
                    Text("${carDetail!!.carYear}")
                }

                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()

                ){
                    Text("Patente:")
                    Text("${carDetail!!.licensePlate}")
                }

                Spacer(modifier = Modifier.height(16.dp))


                Text("Datos de la surcusal:", fontWeight = FontWeight.Bold, fontSize = 20.sp)

                Spacer(modifier = Modifier.height(20.dp))


                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                ){
                    Text("Nombre de la surcusal:")
                    Text("${carDetail!!.branch.name}")
                }

                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                ){
                    Text("Ciudad de la surcusal:")
                    Text("${carDetail!!.branch.city}")
                }

                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                ){
                    Text("Dirección:")
                    Text("${carDetail!!.branch.address}")
                }

                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                ){
                    Text("Numero de telefono:")
                    Text("${carDetail!!.branch.phone}")
                }

                Row(
                    modifier = Modifier
                        .padding(top = 16.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Absolute.SpaceAround
                ) {
                    Button(onClick = {
                        navController.popBackStack()
                    }) {
                        Text("Volver")
                    }

                    Button(onClick = {
                        navController.navigate("updateCar/${carDetail!!.id}")
                    }) {
                        Text("Editar")
                    }

                    Button(
                        onClick = {
                        viewModel.deleteCar(carDetail!!.id)
                        navController.navigate("home")
                    }) {
                        Text("Borrar")
                    }

                }

            }
        }
    }
}