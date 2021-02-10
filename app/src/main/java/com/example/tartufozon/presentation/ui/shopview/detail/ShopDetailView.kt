package com.example.tartufozon.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollableColumn
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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
    ScrollableColumn(modifier = Modifier.fillMaxWidth()) {
        shop.image_url?.let { url ->
            val image = loadPicture(url = url, defaultImage = DEFAULT_FOOD_IMAGE).value
            image?.let { img ->
                Image(
                    bitmap = img.asImageBitmap(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .preferredHeight(IMAGE_HEIGHT.dp),
                    contentScale = ContentScale.Crop,
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                shop.shopName.let { title ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 4.dp)
                    ) {
                        Text(
                            text = title,
                            modifier = Modifier
                                .fillMaxWidth(0.85f)
                                .wrapContentWidth(Alignment.Start),
                            style = MaterialTheme.typography.h3
                        )
                        Text(
                            text = shop.id.toString(),
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentWidth(Alignment.End)
                                .align(Alignment.CenterVertically),
                            style = MaterialTheme.typography.h5
                        )
                        Text(
                            text = shop.location.toString(),
                            modifier = Modifier
                                .fillMaxWidth(0.85f)
                                .wrapContentWidth(Alignment.Start),
                            style = MaterialTheme.typography.h5
                        )
                    }
                }
            }
        }
    }
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