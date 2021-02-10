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
    ScrollableColumn(modifier = Modifier.fillMaxWidth()) {
        truffle.image_url?.let { url ->
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
                truffle.tartufoName?.let { title ->
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
                        val rank = truffle.rating.toString()
                        Text(
                            text = rank,
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentWidth(Alignment.End)
                                .align(Alignment.CenterVertically),
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
fun defaultPreview(){
    val truffle = Truffle(
        1,
        "Tartufinho",
        "Buonisssimo",
        "https://www.moscatotartufi.it/wp-content/uploads/2015/03/vendita-tartufo-bianco-pregiato.jpg",
        9)
    TruffleDetailView(truffle = truffle)
}