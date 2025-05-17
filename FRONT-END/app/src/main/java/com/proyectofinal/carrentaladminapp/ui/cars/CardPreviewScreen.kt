package com.proyectofinal.carrentaladminapp.ui.cars

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.proyectofinal.carrentaladminapp.R
import com.proyectofinal.carrentaladminapp.data.model.Car

@Composable
fun CarPreviewScreen(car: Car){

    Card(
        onClick = {/* TODO */},
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)

    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Image(
                painter = painterResource(id = R.drawable.car_preview),
                contentDescription = "Car Image",
                modifier = Modifier
                    .fillMaxWidth()
                    .size(160.dp)
                    .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))     .background(Color.LightGray), // mientras carga
                contentScale = ContentScale.Crop
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp,)
            )
            {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(car.model, fontWeight = FontWeight.Bold, fontSize = 20.sp)
                    Spacer(modifier = Modifier.weight(1f))
                    Text(car.brand,fontWeight = FontWeight.Medium, fontSize = 20.sp)
                }
                Spacer(modifier = Modifier.height(8.dp))

                Row {
                    Text(car.branchName, fontWeight = FontWeight.Medium , fontSize = 18.sp)
                    Spacer(modifier = Modifier.weight(1f))
                    Text(car.branchCity, fontWeight = FontWeight.Medium, fontSize = 18.sp)
                }

                Box(
                    modifier = Modifier
                        .background(Color.Green, shape = RoundedCornerShape(12.dp))
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text("Disponible", color = Color.Black, fontSize = 12.sp)
                }

            }
        }
    }
}

//@Preview()
//@Composable
//fun CarPreviewScreenPreview(){
//    CarPreviewScreen()
//}