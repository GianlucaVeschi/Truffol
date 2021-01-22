package com.example.tartufozon.presentation.ui.profileview

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun ProfileScreen(color : Color) {
    Box(
        Modifier
            .fillMaxSize()
            .background(color)) {
    }
}

@Preview
@Composable
fun PreviewProfileScreen(){
    ProfileScreen(Color.Cyan)
}