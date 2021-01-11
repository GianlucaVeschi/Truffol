package com.example.tartufozon.presentation.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.BabyChangingStation
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Fragmentz(val route: String, val label: String, val icon: ImageVector) {
    object TruffleListFragment: Fragmentz("truffleListFragment", "TruffleListFragment", Icons.Default.AccountBox)
    object TruffleDetailFragment: Fragmentz("truffleDetailFragment", "TruffleDetailFragment", Icons.Default.ThumbUp)
    object ProfileFragment : Fragmentz("profileFragment","ProfileFragment",Icons.Default.BabyChangingStation)
}