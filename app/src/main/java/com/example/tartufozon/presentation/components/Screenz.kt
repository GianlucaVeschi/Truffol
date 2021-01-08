package com.example.tartufozon.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp

sealed class Screen(val route: String, val label: String, val icon: ImageVector) {
    object Account: Screen("account", "Account", Icons.Default.AccountBox)
    object ThumbUp: Screen("like", "Like", Icons.Default.ThumbUp)
    object DateRange: Screen("date", "Date", Icons.Default.DateRange)
    object Edit: Screen("edit", "Edit", Icons.Default.Edit)
}

@Composable
fun AccountScreen() {
    Text(text = "Account",
        style = TextStyle(color = Color.Black, fontSize = 36.sp),
        textAlign = TextAlign.Center,
        modifier = Modifier.fillMaxSize().background(Color.Cyan))
}

@Composable
fun DateScreen() {
    Text(text = "Date",
        style = TextStyle(color = Color.Black, fontSize = 36.sp),
        textAlign = TextAlign.Center,
        modifier = Modifier.fillMaxSize().background(Color.Magenta))
}

@Composable
fun EditScreen() {
    Text(text = "Edit",
        style = TextStyle(color = Color.Black, fontSize = 36.sp),
        textAlign = TextAlign.Center,
        modifier = Modifier.fillMaxSize().background(Color.LightGray))
}

@Composable
fun ThumpUpScreen() {
    Text(text = "ThumpUp",
        style = TextStyle(color = Color.Black, fontSize = 36.sp),
        textAlign = TextAlign.Center,
        modifier = Modifier.fillMaxSize().background(Color.Green))
}