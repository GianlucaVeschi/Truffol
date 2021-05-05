package com.example.truffol.presentation.ui.truffleview.chips

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.example.truffol.presentation.components.NothingHere
import com.example.truffol.presentation.components.TruffleDetailView
import com.example.truffol.presentation.ui.truffleview.detail.TruffleDetailEvent
import com.example.truffol.presentation.ui.truffleview.detail.TruffleDetailViewModel
import com.example.truffol.util.Constants
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@Composable
fun RecipesScreen() {
    Box(modifier = Modifier.fillMaxWidth()) {
        Text(text = "Recipes", textAlign = TextAlign.Center)
    }
}

@ExperimentalCoroutinesApi
@Composable
@Preview
fun PreviewRecipesScreen() {
    RecipesScreen()
}