package com.example.tartufozon.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.example.tartufozon.domain.model.Truffle

sealed class Screen(val route: String, val label: String, val icon: ImageVector) {
    object ShopsList: Screen("shopslist", "ShopsList", Icons.Default.AccountBox)
    object TrufflesList: Screen("truffleslist", "TrufflesList", Icons.Default.ThumbUp)
    object Profile: Screen("profile", "Profile", Icons.Default.DateRange)
}

sealed class Fragment(val route: String, val label: String, val icon: ImageVector) {
    class TruffleListFragment: Fragment("TruffleListFragment", "truffleListFragment", Icons.Default.AccountBox)
    class TruffleDetailFragment: Fragment("TruffleDetailFragment", "truffleDetailFragment", Icons.Default.ThumbUp)
}

@Composable
fun ShopsList() {
    Text(text = "ShopsList",
        style = TextStyle(color = Color.Black, fontSize = 36.sp),
        textAlign = TextAlign.Center,
        modifier = Modifier.fillMaxSize().background(Color.Cyan))
}

@Composable
fun TrufflesList() {
    Text(text = "TrufflesList",
        style = TextStyle(color = Color.Black, fontSize = 36.sp),
        textAlign = TextAlign.Center,
        modifier = Modifier.fillMaxSize().background(Color.Magenta))
}

@Composable
fun Profile() {
    Text(text = "Profile",
        style = TextStyle(color = Color.Black, fontSize = 36.sp),
        textAlign = TextAlign.Center,
        modifier = Modifier.fillMaxSize().background(Color.LightGray))
}

@Composable
fun BuildRecyclerView(truffles: List<Truffle>, isLoading: Boolean) {
    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn {
            itemsIndexed(
                items = truffles
            ) { index, truffle ->
                TruffleCard(truffle, onClick = {})
            }
        }
        CircularIndeterminateProgressBar(isDisplayed = isLoading, verticalBias = 0.5f)
    }
}

