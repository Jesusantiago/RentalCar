package com.proyectofinal.carrentaladminapp.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.proyectofinal.carrentaladminapp.R
import com.proyectofinal.carrentaladminapp.ui.home.HomeViewModel

data class BrandFilter(val name: String, val imageRes: Int)

val brandFilter = listOf(
    BrandFilter("Alfa Romeo", R.drawable.logo_alfaromeo),
    BrandFilter("Astor Martin", R.drawable.logo_astormartin),
    BrandFilter("Audi", R.drawable.logo_audi),
    BrandFilter("BMW", R.drawable.logo_bmw),
    BrandFilter("Fiat", R.drawable.logo_fiat),
    BrandFilter("Chevrolet", R.drawable.logo_chevrolet),
    BrandFilter("Ferrari", R.drawable.logo_ferrari),
    BrandFilter("Ford", R.drawable.logo_ford),
    BrandFilter("Honda", R.drawable.logo_honda),
    BrandFilter("Citroen", R.drawable.logo_citroen)

)

@Composable
fun FilterForBrand(viewModel: HomeViewModel) {

    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp)
    ) {
        items(brandFilter) { brand ->
            Card(
                onClick = { viewModel.getCarsByBrand(brand.name) },
                modifier = Modifier
                    .padding(end = 8.dp)
                    .size(85.dp)
            ) {
                Image(
                    painter = painterResource(id = brand.imageRes),
                    contentDescription = "logo ",
                    modifier = Modifier
                        .fillMaxSize()
                )
            }
        }


    }
}
