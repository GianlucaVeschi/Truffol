package com.example.tartufozon.presentation.ui.shopview.list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.HiltViewModelFactory
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigate
import androidx.navigation.compose.rememberNavController
import com.example.tartufozon.presentation.components.CircularIndeterminateProgressBar
import com.example.tartufozon.presentation.components.ShopCard
import com.example.tartufozon.presentation.components.shimmer.LoadingListShimmer
import com.example.tartufozon.presentation.ui.DetailScreens
import com.example.tartufozon.presentation.ui.Screens
import com.example.tartufozon.presentation.ui.shopview.detail.ShopDetailScreen
import com.example.tartufozon.presentation.ui.shopview.detail.ShopDetailViewModel
import com.example.tartufozon.presentation.ui.truffleview.detail.TruffleDetailViewModel
import com.example.tartufozon.util.Constants.SHOP_KEY
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@Composable
fun ShopListScreen(
    shopListViewModel: ShopListViewModel
) {

    val navController: NavHostController = rememberNavController()

    NavHost(navController, startDestination = Screens.ShopListScreen.route) {

        composable(Screens.ShopListScreen.route) {
            ShopListScreenContent(shopListViewModel, navController)
        }

        composable(DetailScreens.ShopDetailScreen.route) {
            val factory = HiltViewModelFactory(LocalContext.current, it)
            val shopDetailViewModel: ShopDetailViewModel = viewModel("ShopDetailViewModel", factory)
            ShopDetailScreen(navController, shopDetailViewModel)
        }

    }
}

@ExperimentalCoroutinesApi
@Composable
fun ShopListScreenContent(shopListViewModel: ShopListViewModel, navController: NavController) {

    val shopList = shopListViewModel.shopList.value
    val loading = shopListViewModel.loading.value
    val scrollState = rememberScrollState()

    Scaffold {
        Box(
            modifier = Modifier.background(color = MaterialTheme.colors.surface)
        ) {
            if (loading) {
                LoadingListShimmer(imageHeight = 250.dp)
            } else {
                LazyColumn {
                    itemsIndexed(
                        items = shopList
                    ) { index, shop ->
                        ShopCard(shop) {
                            navController.currentBackStackEntry?.arguments?.putInt(
                                SHOP_KEY,
                                shop.id
                            )
                            navController.navigate(DetailScreens.ShopDetailScreen.route)
                        }
                    }
                }
            }
            CircularIndeterminateProgressBar(isDisplayed = loading, 0.5f)
        }
    }
}