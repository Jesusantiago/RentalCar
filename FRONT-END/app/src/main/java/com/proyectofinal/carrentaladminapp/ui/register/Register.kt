package com.proyectofinal.carrentaladminapp.ui.register

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons // <- FALTANTE
import androidx.compose.material.icons.filled.ArrowBack // <- FALTANTE
import androidx.compose.material3.* // <- Importa todos los componentes de Material3
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.proyectofinal.carrentaladminapp.data.model.RegisterRequest
import com.proyectofinal.carrentaladminapp.data.remote.RetrofitClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun RegisterScreen(navController: NavHostController) {
    val context = LocalContext.current

    var userName by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var licenseType by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp)
    ) {
        // Botón para ir hacia atrás, con desplazamiento absoluto
        IconButton(
            onClick = { navController.popBackStack() },
            modifier = Modifier.offset(x = 16.dp, y = 16.dp) // Ajusta el valor de x e y a tu gusto
        ) {
            Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Volver atrás")
        }

        Column(
            modifier = Modifier
                .align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Registrate", style = MaterialTheme.typography.headlineMedium)

            Spacer(modifier = Modifier.height(24.dp))

            OutlinedTextField(
                value = userName,
                onValueChange = { userName = it },
                label = { Text("Nombre de usuario")},
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Nombre")},
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = lastName,
                onValueChange = { lastName = it },
                label = { Text("Apellido")},
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Correo")},
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Contraseña")},
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = confirmPassword,
                onValueChange = { confirmPassword = it },
                label = { Text("Confirma la contraseña")},
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = licenseType,
                onValueChange = { licenseType = it },
                label = { Text("Tipo de licencia")},
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    CoroutineScope(Dispatchers.IO).launch {
                        try {
                            val response = RetrofitClient.authService.register(
                                RegisterRequest(userName, name, lastName, email, password, licenseType)
                            )
                            if (response.isSuccessful) {
                                launch(Dispatchers.Main) {
                                    Toast.makeText(context, "Registro exitoso", Toast.LENGTH_LONG).show()
                                    navController.navigate("login_username")
                                }
                            } else {
                                launch(Dispatchers.Main) {
                                    println(response.code())
                                    Toast.makeText(context, "Error: ${response.code()}", Toast.LENGTH_LONG).show()
                                }
                            }
                        } catch (e: Exception) {
                            launch(Dispatchers.Main) {
                                println(e.message)
                                Toast.makeText(context, "Error: ${e.message}", Toast.LENGTH_LONG).show()
                            }
                        }
                    }
                },
                enabled = email.isNotBlank() && password.isNotBlank() && confirmPassword.isNotBlank()
                        && name.isNotBlank() && lastName.isNotBlank() && licenseType.isNotBlank(),
                modifier = Modifier.fillMaxWidth()
            ){
                Text("Registrarte")
            }

            Spacer(modifier = Modifier.height(32.dp))
        }

        Button(
            onClick = {
                navController.popBackStack()
            },
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(16.dp)
        ) {
            Text("¡Ya tienes cuenta! Ingresa acá")
        }
    }
}
