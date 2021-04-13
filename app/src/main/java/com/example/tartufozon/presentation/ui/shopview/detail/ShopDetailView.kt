package com.example.tartufozon.presentation.components

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.font.FontWeight.Companion.W700
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tartufozon.domain.model.Shop
import com.example.tartufozon.util.Constants.IMAGE_HEIGHT
import com.example.tartufozon.util.DEFAULT_FOOD_IMAGE
import com.example.tartufozon.util.loadPicture
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@Composable
fun ShopDetailView(
    shop: Shop,
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .then(Modifier.padding(8.dp)),
    ) {
        item {
            val image = loadPicture(url = shop.image_url, defaultImage = DEFAULT_FOOD_IMAGE).value

            DisplayImage(image)
            DisplayTitle(shop.shopName)
            DisplayInfo("Location", shop.location)
            DisplayInfo("Email", shop.email)
            DisplayInfo("Phone", shop.phone)
            DisplayInfo("Website", shop.website)
            DisplayInfo("Info", shop.description)
        }
    }
}

@Composable
fun DisplayTitle(shopName: String) {
    Text(
        text = shopName,
        modifier = Modifier
            .fillMaxWidth(0.85f)
            .wrapContentWidth(Alignment.Start),
        style = MaterialTheme.typography.h3
    )
}

@Composable
fun DisplayImage(image: Bitmap?) {
    image?.let { img ->
        Image(
            bitmap = img.asImageBitmap(),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(IMAGE_HEIGHT.dp),
            contentScale = ContentScale.Fit,
        )
    }
}

@Composable
fun DisplayInfo(infotype: String, info: String) {
    Text(
        buildAnnotatedString {
            withStyle(
                style = SpanStyle(
                    fontSize = 22.sp,
                    fontWeight = Bold
                )
            ) {
                append("$infotype: ")
            }
            withStyle(
                style = SpanStyle(
                    fontSize = 22.sp,
                )
            ) {
                append(info)
            }
        })
    Divider(thickness = 4.dp)
}

@ExperimentalCoroutinesApi
@Preview
@Composable
fun ShopDetailScreenPreview() {
    val shop = Shop(
        id = 10,
        shopName = "Tartufo Valmetauro Di Sartori Manuel",
        description = "La Ditta Sartori custode di questa antica tradizione, raccoglie e seleziona soltanto i frutti migliori, quelli cioè che hanno raggiunto il giusto grado di maturazione, e ne racchiude le proprietà con l’attenzione di chi ama il proprio lavoro, riuscendo così a restituirne integralmente tutta la fragranza.",
        image_url = "http://valmetauro.com/wp-content/themes/infringe/images/logo-azienda.png",
        website = "http://www.valmetauro.com/",
        location = "Via Ada Negri, 5 - 61033 FERMIGNANO (PU)",
        email = "info@valmetauro.com",
        phone = "+390722330324"
    )
    ShopDetailView(shop = shop)
}