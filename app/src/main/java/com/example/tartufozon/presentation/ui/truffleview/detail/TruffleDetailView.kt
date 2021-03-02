package com.example.tartufozon.presentation.components

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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tartufozon.domain.model.Truffle
import com.example.tartufozon.util.Constants.IMAGE_HEIGHT
import com.example.tartufozon.util.DEFAULT_FOOD_IMAGE
import com.example.tartufozon.util.loadPicture
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@Composable
fun TruffleDetailView(
    truffle: Truffle,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        val img = loadPicture(url = truffle.image_url, defaultImage = DEFAULT_FOOD_IMAGE).value
        img?.let {
            Image(
                bitmap = it.asImageBitmap(),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(IMAGE_HEIGHT.dp),
                contentScale = ContentScale.Crop,
            )
        }
        Divider(thickness = 4.dp)
        Text(
            text = truffle.tartufoName,
            modifier = Modifier
                .fillMaxWidth(0.85f)
                .wrapContentWidth(Alignment.Start),
            style = MaterialTheme.typography.h3
        )
        Divider(thickness = 4.dp)
        Text(
            text = truffle.rating.toString(),
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentWidth(Alignment.Start),
            style = MaterialTheme.typography.h5
        )
    }
}


@ExperimentalCoroutinesApi
@Preview
@Composable
fun defaultPreview() {
    val truffle = Truffle(
        1,
        "Tartufinho",
        "Buonisssimo",
        "https://www.moscatotartufi.it/wp-content/uploads/2015/03/vendita-tartufo-bianco-pregiato.jpg",
        9
    )
    TruffleDetailView(truffle = truffle)
}