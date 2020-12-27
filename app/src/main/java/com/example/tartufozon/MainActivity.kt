package com.example.tartufozon

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollableColumn
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.imageFromResource
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.setContent
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //defines the activity's layout.
        setContent {
            ScrollableColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .background(color = Color(0xFFF2F2F2))
            ) {
                Image(
                    bitmap = imageFromResource(
                        res = resources,
                        resId = R.drawable.tartufo_bianco
                    ),
                    modifier = Modifier.height(300.dp),
                    contentScale = ContentScale.Crop,
                )
                Column(modifier = Modifier.padding(16.dp)) {
                    myText(text = "Tartufo Bianco", fontSize = 26)
                    Spacer(modifier = Modifier.padding(top = 8.dp))
                    myText(text = "800 calories",17)
                    Spacer(modifier = Modifier.padding(top = 8.dp))
                    myText(text = "\$5.99",17)
                }
            }
        }
    }

    @Composable
    fun myText(text: String, fontSize : Int) {
        Text(
            text = text,
            style = TextStyle(
                fontSize = TextUnit.Companion.Sp(fontSize),
            )
        )
    }
}