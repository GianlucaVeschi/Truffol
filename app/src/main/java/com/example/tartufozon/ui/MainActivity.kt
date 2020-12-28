package com.example.tartufozon.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.TextUnit
import com.example.tartufozon.R
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var someRandomString: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Timber.d("onCreate: ${someRandomString}")
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