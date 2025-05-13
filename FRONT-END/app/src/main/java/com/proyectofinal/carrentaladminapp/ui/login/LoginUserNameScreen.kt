package com.proyectofinal.carrentaladminapp.login

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack

import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

@Composable
fun LoginUserNameScreen(navController: NavHostController) {
    // Estados locales para email y password
    var userName by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
    ){
        IconButton(
            onClick = { navController.popBackStack() },
            modifier = Modifier.offset(x = -20.dp, y = 32.dp)
        ) {
            Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Volver atrás")
        }

        Column(
            modifier = Modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {


            Text(
                "Ingresá tu correo electrinico",
                style = MaterialTheme.typography.titleLarge
            )

            Spacer(modifier = Modifier.height(24.dp))

            OutlinedTextField(
                value = userName,
                onValueChange = { userName = it },
                label = { Text("Email") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    navController.navigate("login_password/$userName")
                },
                enabled = userName.isNotBlank(),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Siguiente")
            }
        }

        // Este botón sí puede ir abajo centrado
        TextButton(
            onClick = {
                navController.navigate("register")
            },
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(16.dp)
        ) {
            Text("¿No tienes cuenta? Regístrate")
        }
    }
}



@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    val navController = rememberNavController()
    LoginUserNameScreen(navController)
}
