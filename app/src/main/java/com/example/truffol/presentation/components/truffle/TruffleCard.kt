package com.example.truffol.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
import com.example.truffol.domain.model.Truffle
import com.example.truffol.util.DEFAULT_FOOD_IMAGE
import com.example.truffol.util.loadPicture
import kotlinx.coroutines.ExperimentalCoroutinesApi
import java.security.SecureRandom

@kotlinx.coroutines.ExperimentalCoroutinesApi
@Composable
fun TruffleCard(
    truffle: Truffle,
    onClick: () -> Unit,
) {
    Card(
        shape = MaterialTheme.shapes.small,
        modifier = Modifier
            .padding(
                bottom = 6.dp,
                top = 6.dp,
            )
            .fillMaxWidth()
            .clickable(onClick = onClick),
        elevation = 8.dp,
    ) {
        Column() {

            DisplayCardImage(truffle.image_url)

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp, bottom = 12.dp, start = 8.dp, end = 8.dp)
            ) {
                Text(
                    text = truffle.tartufoName,
                    modifier = Modifier
                        .wrapContentWidth(Alignment.Start)
                        .align(Alignment.CenterVertically),
                    style = MaterialTheme.typography.h5
                )

                Spacer(modifier = Modifier.width(30.dp))
                val secureRandom = SecureRandom()
                val randomNumber = secureRandom.nextInt(100)
                Text(
                    text = "€${truffle.price}/100g",
                    modifier = Modifier.align(Alignment.CenterVertically),
                    style = MaterialTheme.typography.h6
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

@ExperimentalCoroutinesApi
@Composable
fun DisplayCardImage(imageUrl : String){
    imageUrl.let { url ->
        val image = loadPicture(url = url, defaultImage = DEFAULT_FOOD_IMAGE).value
        image?.let { img ->
            Image(
                bitmap = img.asImageBitmap(),
                contentDescription = "Truffle Card image",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(225.dp),
                contentScale = ContentScale.Fit,
            )
        }
    }
}
@ExperimentalCoroutinesApi
@Preview
@Composable
fun PreviewTruffleCard() {
    TruffleCard(
        truffle = Truffle(
            1,
            "Tartufinho",
            "Buonisssimo",
            "https://www.moscatotartufi.it/wp-content/uploads/2015/03/vendita-tartufo-bianco-pregiato.jpg",
            9,
            99
        ),
        onClick = {
            // TODO: 08.02.21 onCLICK Truffle
        })
}

