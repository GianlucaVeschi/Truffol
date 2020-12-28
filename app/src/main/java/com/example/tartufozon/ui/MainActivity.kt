package com.example.tartufozon.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.TextUnit
import com.example.tartufozon.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    @Composable
    fun exampleText(text: String, fontSize: Int) {
        Text(
            text = text,
            style = TextStyle(
                fontSize = TextUnit.Companion.Sp(fontSize),
            )
        )
    }

    companion object {
        private const val TAG = "MainActivity"
    }
}