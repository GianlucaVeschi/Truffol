package com.example.tartufozon

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.TextUnit

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //defines the activity's layout.
//        setContent {
//            ScrollableColumn(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .fillMaxHeight()
//                    .background(color = Color(0xFFF2F2F2))
//            ) {
//                Image(
//                    bitmap = imageFromResource(
//                        res = resources,
//                        resId = R.drawable.tartufo_bianco
//                    ),
//                    modifier = Modifier.height(300.dp),
//                    contentScale = ContentScale.Crop,
//                )
//
//                Column(modifier = Modifier.padding(16.dp)) {
//                    myText(text = "Tartufo Bianco", fontSize = 25)
//                    Spacer(modifier = Modifier.padding(top = 8.dp))
//
//                    Row(
//                        modifier = Modifier.fillMaxWidth(),
//                        horizontalArrangement = Arrangement.SpaceBetween
//                    ) {
//                        Text(
//                            text = "Gallo di Petriano",
//                            modifier = Modifier.align(Alignment.CenterVertically)
//                        )
//                        Text(text = "Dicembre 2020",
//                            modifier = Modifier.align(Alignment.CenterVertically)
//                        )
//                    }
//
//                    Spacer(modifier = Modifier.padding(top = 8.dp))
//                    myText(text = "800 calories", 17)
//                    Spacer(modifier = Modifier.padding(top = 8.dp))
//                    myText(text = "\$5.99", 17)
//                }
//            }
//        }
    }
    companion object{
        private const val TAG = "MainActivity"
    }

    @Composable
    fun myText(text: String, fontSize: Int) {
        Text(
            text = text,
            style = TextStyle(
                fontSize = TextUnit.Companion.Sp(fontSize),
            )
        )
    }
}