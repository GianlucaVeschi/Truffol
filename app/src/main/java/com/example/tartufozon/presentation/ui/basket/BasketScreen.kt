package com.example.tartufozon.presentation.ui.basket

import androidx.compose.foundation.layout.*
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.tartufozon.presentation.components.NothingHere

@ExperimentalMaterialApi
@Composable
fun BasketScreen() {
    Box(
        modifier = Modifier.fillMaxSize()
    ){
        Column(modifier = Modifier.align(Alignment.Center)){
            Text(
                modifier = Modifier
                    .padding(8.dp)
                    .align(Alignment.CenterHorizontally),
                text = "¯\\_(ツ)_/¯",
                style = TextStyle(fontSize = 55.sp)
            )
            Text(
                modifier = Modifier
                    .padding(8.dp)
                    .align(Alignment.CenterHorizontally),
                text = "Qui non c'é niente, But Soon you will be able to save your favorite truffle recipes here",
                style = TextStyle(fontSize = 30.sp)
            )
        }
    }
}

@ExperimentalMaterialApi
@Composable
fun BasketScreenContent() {
    NothingHere()
}

@ExperimentalMaterialApi
@Preview
@Composable
fun previewBasketScreenContent(){
    BasketScreenContent()
}
