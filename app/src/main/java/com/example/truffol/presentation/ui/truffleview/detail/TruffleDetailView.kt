package com.example.truffol.presentation.components

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
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.truffol.domain.model.Truffle
import com.example.truffol.util.Constants.IMAGE_HEIGHT
import com.example.truffol.util.DEFAULT_FOOD_IMAGE
import com.example.truffol.util.loadPicture
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@Composable
fun TruffleDetailView(
    truffle: Truffle,
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .then(Modifier.padding(8.dp)),
    ) {
        item {
            val image = loadPicture(url = truffle.image_url, defaultImage = DEFAULT_FOOD_IMAGE).value

            DisplayTruffleImage(image)
            DisplayTruffleTitle(truffle.tartufoName)
            DisplayTruffleInfo(infotype = "Rating", info = truffle.rating.toString())
            DisplayTruffleInfo(infotype = "Price", info = truffle.price.toString())
            DisplayTruffleInfo(infotype = "Description", info = truffle.description)
        }
    }
}

@Composable
fun DisplayTruffleTitle(shopName: String) {
    Text(
        text = shopName,
        modifier = Modifier
            .fillMaxWidth(0.85f)
            .wrapContentWidth(Alignment.Start),
        style = MaterialTheme.typography.h3
    )
}

@Composable
fun DisplayTruffleImage(image: Bitmap?) {
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
fun DisplayTruffleInfo(infotype: String, info: String) {
    Text(
        buildAnnotatedString {
            withStyle(
                style = SpanStyle(
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
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
fun TruffleDetailScreenPreview() {
    val truffle = Truffle(
        1,
        "Tartufinho",
        "Buonisssimo",
        "https://www.moscatotartufi.it/wp-content/uploads/2015/03/vendita-tartufo-bianco-pregiato.jpg",
        9,
        99
    )
    TruffleDetailView(truffle = truffle)
}