package com.example.tartufozon.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tartufozon.domain.model.Shop
import com.example.tartufozon.util.DEFAULT_SHOP_IMAGE
import com.example.tartufozon.util.loadPicture

@kotlinx.coroutines.ExperimentalCoroutinesApi
@Composable
fun ShopCard(
    shop: Shop,
    onClick: () -> Unit,
) {
    Card(
        shape = MaterialTheme.shapes.small,
        modifier = Modifier
            .padding(
                start = 10.dp,
                bottom = 6.dp,
                top = 6.dp,
                end = 8.dp
            )
            .fillMaxWidth()
            .clickable(onClick = onClick),
        elevation = 8.dp,
    ) {
        Column() {
            shop.image_url.let { url ->
                val image = loadPicture(url = url, defaultImage = DEFAULT_SHOP_IMAGE).value
                image?.let { img ->
                    Image(
                        bitmap = img.asImageBitmap(),
                        contentDescription = "Shop Card image",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(225.dp),
                        contentScale = ContentScale.Fit,
                    )
                }
            }
            shop.shopName.let { title ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 12.dp, bottom = 12.dp, start = 8.dp, end = 8.dp)
                ) {
                    Text(
                        text = title,
                        modifier = Modifier
                            .fillMaxWidth(0.85f)
                            .wrapContentWidth(Alignment.Start),
                        style = MaterialTheme.typography.h5
                    )
                    val iconFilled = mutableStateOf(false)
                    IconToggleButton(
                        checked = iconFilled.value,
                        onCheckedChange = { iconFilled.value = true }, // TODO: Add to fav list
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentWidth(Alignment.End)
                            .align(Alignment.CenterVertically),
                        content = {
                            Icon(
                                imageVector = Icons.Default.FavoriteBorder,
                                contentDescription = "Add to Favorites"
                            )
                        }
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewShopCard() {
    ShopCard(
        shop = Shop(
            id = 1,
            shopName = "Acqualagna tartufi",
            description = "Molto famoso",
            image_url = "https=//www.acqualagnatartufi.com/wp-content/uploads/2020/10/acqualagna-tartufi-logo-1.png",
            website = "https=//www.acqualagnatartufi.com/",
            location = "Acqualagna",
            email = "info@acqualagnatartufi.com",
            phone = "0721799310"
        ),
        onClick = {
            // TODO: 08.02.21 onCLICK SHOP
        })
}