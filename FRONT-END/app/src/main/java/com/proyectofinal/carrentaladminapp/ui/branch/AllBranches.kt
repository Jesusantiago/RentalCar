package com.proyectofinal.carrentaladminapp.ui.branch

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.proyectofinal.carrentaladminapp.R
import com.proyectofinal.carrentaladminapp.data.model.BranchDTO
import com.proyectofinal.carrentaladminapp.data.model.UpdateCarDTO
import com.proyectofinal.carrentaladminapp.ui.home.HomeViewModel

@Composable
fun AllBranchesScreen(
    viewModel: HomeViewModel,
    paddingValues: PaddingValues,
    navController: NavHostController
) {

    LaunchedEffect(Unit) {
        viewModel.getAllBranches()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            text = "Todas las sucursales",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(16.dp)
        )

        viewModel.branchList.forEach { branch ->
            BranchCard(branch)
            Spacer(modifier = Modifier.height(16.dp))
        }

        Row(
            modifier = Modifier
                .padding(top = 16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Absolute.SpaceAround
        ) {
            Button(
                onClick = {viewModel.deleteBranch(11L)},
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.error ,
                    contentColor = Color.White
                )
            ) {
                Text("Borrar")
            }

            Button(
                onClick = {navController.navigate("createBranch")},
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF4CAF50),
                    contentColor = Color.White
                )
            ) {
                Text("Agregar")
            }
        }
    }
}

@Composable
fun BranchCard(branch: BranchDTO) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal =  16.dp)
        ,
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(vertical = 4.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.store_profile_back),
                    contentDescription = "User Icon",
                    modifier = Modifier
                        .size(64.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Column {
                    Text(text = branch.name)
                    Text(text = branch.address)
                    Text(text = branch.city)
                    Text(text = branch.phone)

                }
            }
        }
    }
}