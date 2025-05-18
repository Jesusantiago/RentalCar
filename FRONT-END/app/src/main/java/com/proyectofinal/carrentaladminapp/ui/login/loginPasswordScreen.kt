package com.proyectofinal.carrentaladminapp.ui.login

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack

import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.proyectofinal.carrentaladminapp.data.model.LoginRequest
import com.proyectofinal.carrentaladminapp.data.remote.RetrofitClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun LoginPasswordScreen(navController: NavHostController, emailUser: String) {
    val context = LocalContext.current
    var password by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp)
    ) {
        IconButton(
            onClick = { navController.popBackStack() },
            modifier = Modifier.offset(x = 0.dp, y = 0.dp)
        ) {
            Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Volver atrás")
        }

        Column(
            modifier = Modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                "Hola $emailUser",
                style = MaterialTheme.typography.titleLarge
            )

            Spacer(modifier = Modifier.height(24.dp))

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Contraseña") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    CoroutineScope(Dispatchers.IO).launch {
                        try {
                            val response = RetrofitClient.authService.login(
                                LoginRequest(emailUser, password)
                            )
                            if (response.isSuccessful) {
                                launch(Dispatchers.Main) {
                                    Toast.makeText(context, "Login exitoso", Toast.LENGTH_LONG).show()
                                    navController.navigate("admin")
                                }
                            } else {
                                launch(Dispatchers.Main) {
                                    Toast.makeText(context, "Error: ${response.code()}", Toast.LENGTH_LONG).show()
                                }
                            }
                        } catch (e: Exception) {
                            launch(Dispatchers.Main) {
                                Toast.makeText(context, "Error: ${e.message}", Toast.LENGTH_LONG).show()
                            }
                        }
                    }
                },
                enabled = password.isNotBlank(),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Siguiente")
            }
        }
    }
}




@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    val navController = rememberNavController()
    LoginPasswordScreen(navController, "Jesus")
}
