package com.example.truffol.presentation.components

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import com.example.truffol.domain.model.Shop
import com.example.truffol.util.Constants.IMAGE_HEIGHT
import com.example.truffol.util.DEFAULT_FOOD_IMAGE
import com.example.truffol.util.loadPicture
import kotlinx.coroutines.ExperimentalCoroutinesApi
import androidx.core.content.ContextCompat.startActivity


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
            ContactBar(shop.email, shop.phone)
            DisplayInfo("Location", shop.location)
            DisplayInfo("Email", shop.email)
            DisplayInfo("Phone", shop.phone)
            DisplayInfo("Website", shop.website)
            DisplayInfo("Info", shop.description)
        }
    }
}

@Composable
fun ContactBar(email: String, phone: String) {
    Spacer(modifier = Modifier.padding(10.dp))
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth()
    ) {
        val context = LocalContext.current
        Button(
            modifier = Modifier.width(150.dp),
            colors = ButtonDefaults
                .buttonColors(backgroundColor = Color.Black, contentColor = Color.White),
            onClick = {
                val emailIntent = Intent(Intent.ACTION_SENDTO).apply {
                    data = Uri.parse("mailto:${email}")
                }
                ContextCompat.startActivity(
                    context,
                    Intent.createChooser(emailIntent, "Send emal to merchant"),
                    null
                )
            },
        ) {
            Text(
                modifier = Modifier.padding(8.dp),
                text = "Send Email",
                style = TextStyle(fontSize = 15.sp)
            )
        }
        Button(
            modifier = Modifier.width(150.dp),
            colors = ButtonDefaults
                .buttonColors(backgroundColor = Color.Black, contentColor = Color.White),
            onClick = {
                val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel: $phone"))
                startActivity(context, intent, null)
            },
        ) {
            Text(
                modifier = Modifier.padding(8.dp),
                text = "Call",
                style = TextStyle(fontSize = 15.sp)
            )
        }
    }
    Spacer(modifier = Modifier.padding(10.dp))
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
        shopId = 10,
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