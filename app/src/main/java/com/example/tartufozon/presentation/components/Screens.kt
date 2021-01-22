package com.example.tartufozon.presentation.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.BabyChangingStation
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screens(val route: String, val label: String, val icon: ImageVector) {
    object TruffleListScreen: Screens("gotoTruffleListScreen", "Truffles", Icons.Default.AccountBox)
    object ShopListScreen: Screens("gotoShopListScreen", "Shops", Icons.Default.ThumbUp)
    object ProfileScreen : Screens("gotoProfileScreen","Profile",Icons.Default.BabyChangingStation)
}