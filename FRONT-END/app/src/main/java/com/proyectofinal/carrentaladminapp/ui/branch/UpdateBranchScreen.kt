package com.proyectofinal.carrentaladminapp.ui.branch

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.proyectofinal.carrentaladminapp.R
import com.proyectofinal.carrentaladminapp.ui.home.HomeViewModel
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavController
import com.proyectofinal.carrentaladminapp.data.model.BranchDTO
import com.proyectofinal.carrentaladminapp.data.model.UpdateCarDTO


@SuppressLint("ContextCastToActivity")
@Composable
fun UpdateBranchScreen(viewModel: HomeViewModel, paddingValues: PaddingValues, navController: NavController) {
    val branch = viewModel.branch.value ?: return // Evita null

    // Estados para campos editables, inicializados con valores actuales
    var name by remember { mutableStateOf(branch.name) }
    var address by remember { mutableStateOf(branch.address) }
    var city by remember { mutableStateOf(branch.city) }
    var phone by remember { mutableStateOf(branch.phone) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF8F9FA))
            .padding(
                top = 156.dp,
                start = 16.dp,
                end = 16.dp,
                bottom = paddingValues.calculateBottomPadding()
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            painter = painterResource(id = R.drawable.store_profile_back),
            contentDescription = "User Icon",
            modifier = Modifier.size(64.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Ahora el nombre es editable, no solo texto estático
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Nombre") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Los demás campos editables usando InforItem pero modificado para aceptar estados
        InforItem(label = "Dirección", value = address, onValueChange = { address = it })
        InforItem(label = "Ciudad", value = city, onValueChange = { city = it })
        InforItem(label = "Teléfono", value = phone, onValueChange = { phone = it })

        Spacer(modifier = Modifier.height(24.dp))

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
                    val updatedBranch = BranchDTO(name, address, city, phone)
                    viewModel.updateBranch(1L, updatedBranch)
                    navController.navigate("branchDetail")
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

// Modificamos InforItem para aceptar onValueChange y ser editable
@Composable
fun InforItem(label: String, value: String, onValueChange: (String) -> Unit) {
    Column(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
        Text(
            text = label,
            fontSize = 14.sp,
            color = Color.Gray
        )

        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            singleLine = true,
            label = { Text(label) },
            modifier = Modifier.fillMaxWidth()
        )
    }
}


