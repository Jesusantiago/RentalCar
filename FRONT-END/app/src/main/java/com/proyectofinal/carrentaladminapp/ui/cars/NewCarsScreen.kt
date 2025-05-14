package com.proyectofinal.carrentaladminapp.ui.cars

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.proyectofinal.carrentaladminapp.data.model.CarRegisterResponse
import com.proyectofinal.carrentaladminapp.data.remote.RetrofitCar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewCarScreen(navController: NavController) {
    val context = LocalContext.current

    var brand by remember { mutableStateOf("") }
    var model by remember { mutableStateOf("") }
    var carYear by remember { mutableStateOf("") }
    var licensePlate by remember { mutableStateOf("") }
    var statusCar by remember { mutableStateOf("") }
    var branch by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp)
    ) {
        IconButton(
            onClick = { navController.popBackStack() },
            modifier = Modifier.offset(x = 0.dp, y =0.dp)
        ) {
            Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Volver atrás")
        }

        Column(
            modifier = Modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Text(
                "Agrega un nuevo auto",
                style = MaterialTheme.typography.titleLarge
            )

            Spacer(modifier = Modifier.height(24.dp))

            OutlinedTextField(
                value = brand,
                onValueChange = { brand = it},
                label = { Text("Marca")},
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(24.dp))

            OutlinedTextField(
                value = model,
                onValueChange = { model = it},
                label = { Text("Modelo")},
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(24.dp))

            OutlinedTextField(
                value = carYear,
                onValueChange = { carYear = it},
                label = { Text("Año del auto")},
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(24.dp))

            OutlinedTextField(
                value = licensePlate,
                onValueChange = { licensePlate = it },
                label = { Text("Patente del auto")},
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(24.dp))

            OutlinedTextField(
                value = statusCar,
                onValueChange = { statusCar = it},
                label = { Text("Disponibilidad")},
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(24.dp))

            OutlinedTextField(
                value = branch,
                onValueChange = { branch = it},
                label = { Text("Sucursal")},
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(36.dp))


            Button(
                onClick = {
                    CoroutineScope(Dispatchers.IO).launch {
                        try {
                            val response = RetrofitCar.api.newCar(
                                CarRegisterResponse(brand, model, carYear.toInt(),
                                    licensePlate, statusCar, branch.toLong())
                            )

                            launch(Dispatchers.Main) {
                                Toast.makeText(context, "Producto agregado correctamente", Toast.LENGTH_LONG).show()
                                navController.navigate("home")
                            }

                        } catch (e: Exception) {
                            launch( Dispatchers.Main ) {
                                Toast.makeText(context, "Error: ${e.message}", Toast.LENGTH_LONG).show()
                            }
                        }
                    }
                },
                enabled = brand.isNotBlank() && model.isNotBlank()
                        && carYear.isNotBlank() && licensePlate.isNotBlank()
                        && statusCar.isNotBlank() && branch.isNotBlank(),
                modifier = Modifier.fillMaxWidth()
            ){
                Text("Agregar auto")
            }
        }


    }

}

@Preview(showBackground = true)
@Composable
fun NewCarScreenPreview(){
    val navController = rememberNavController()
    NewCarScreen(navController)
}

