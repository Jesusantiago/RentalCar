package com.proyectofinal.carrentaladminapp.ui.branch

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.proyectofinal.carrentaladminapp.R
import com.proyectofinal.carrentaladminapp.ui.home.HomeViewModel

@SuppressLint("ContextCastToActivity")
@Composable
fun BranchDetailScreen(
    viewModel: HomeViewModel,
    paddingValues: PaddingValues,
    navController: NavHostController
) {
    val branch = viewModel.branch.value


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
            modifier = Modifier
                .size(64.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = branch!!.name,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF343A40)
        )

        Spacer(modifier = Modifier.height(16.dp))

        InfoItem(label = "Dirección", value = branch.address)
        InfoItem(label = "Ciudad", value = branch.city)
        InfoItem(label = "Teléfono", value = branch.phone)

        Button(
            onClick = {
                navController.navigate("updateBranch")
            }
        ) {
            Text("Editar")
        }
    }
}

@Composable
fun InfoItem(label: String, value: String) {
    Column(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
        Text(
            text = label,
            fontSize = 14.sp,
            color = Color.Gray
        )
        Text(
            text = value,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            color = Color(0xFF212529)
        )
    }
}
