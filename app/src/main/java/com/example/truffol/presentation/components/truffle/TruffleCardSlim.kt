package com.example.truffol.presentation.components.truffle

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.truffol.domain.model.Truffle
import kotlinx.coroutines.ExperimentalCoroutinesApi
import java.security.SecureRandom

@kotlinx.coroutines.ExperimentalCoroutinesApi
@Composable
fun TruffleCardSlim(
    truffle: Truffle,
    onClick: () -> Unit
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
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp, bottom = 12.dp, start = 8.dp, end = 8.dp)
            ) {
                Text(
                    text = truffle.tartufoName,
                    modifier = Modifier
                        //.fillMaxWidth(0.85f)
                        .wrapContentWidth(Alignment.Start)
                        .align(Alignment.CenterVertically),
                    style = MaterialTheme.typography.h5
                )

                Spacer(modifier = Modifier.width(30.dp))
                val secureRandom = SecureRandom()
                val randomNumber = SecureRandom().nextInt(100)
                Text(
                    text = "${randomNumber}€",
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
@Preview
@Composable
fun PreviewTruffleCardSlim() {
    TruffleCardSlim(
        truffle = Truffle(
            1,
            "Tartufinho Slim",
            "Buonisssimo",
            "https://www.moscatotartufi.it/wp-content/uploads/2015/03/vendita-tartufo-bianco-pregiato.jpg",
            9,
            99
        ),
        onClick = {
            // TODO: 08.02.21 onCLICK Truffle
        })
}