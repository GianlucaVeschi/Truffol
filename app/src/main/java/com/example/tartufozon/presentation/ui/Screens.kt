package com.example.tartufozon.presentation.ui

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

sealed class DetailScreens(val route: String){
    // TODO: 03.02.21  pass truffle instead of string
    object TruffleDetailScreen : DetailScreens("gotoTruffleDetailScreen"){
        val routeWithArg: String = "$route?arg={arg}"
        fun withArg(arg: String): String = routeWithArg.replace("{arg}", arg)
    }
    object ShopDetailScreen : DetailScreens("gotoShopDetailScreen")
}