package com.example.tartufozon.presentation.ui.shopview

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun ShopListScreen(
    shopListViewModel: ShopListViewModel
) {

    //val shopListViewModel : ShopListViewModel = viewModel() // Error
    shopListViewModel.triggerEvent()

    Box(
        Modifier
            .fillMaxSize()
            .background(Color.Cyan)) {
    }
}