package com.example.truffol.presentation.ui.shopview.list

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.*
import androidx.compose.runtime.Composable
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
import com.example.truffol.domain.model.Shop
import com.example.truffol.presentation.components.ShopCard
import com.example.truffol.presentation.components.shimmer.LoadingListShimmer
import com.example.truffol.presentation.components.theme.AppTheme
import com.example.truffol.presentation.ui.DetailScreens
import com.example.truffol.presentation.ui.Screens
import com.example.truffol.presentation.ui.shopview.detail.ShopDetailScreen
import com.example.truffol.presentation.ui.shopview.detail.ShopDetailViewModel
import com.example.truffol.util.Constants.SHOP_KEY
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalMaterialApi
@ExperimentalCoroutinesApi
@Composable
fun ShopListScreen(
    shopListViewModel: ShopListViewModel,
    isNetworkAvailable: Boolean
) {

    val navController: NavHostController = rememberNavController()

    NavHost(navController, startDestination = Screens.ShopListScreen.route) {

        composable(Screens.ShopListScreen.route) {
            ShopListScreenContent(shopListViewModel, navController, isNetworkAvailable)
        }

        composable(DetailScreens.ShopDetailScreen.route) {
            val factory = HiltViewModelFactory(LocalContext.current, it)
            val shopDetailViewModel: ShopDetailViewModel = viewModel("ShopDetailViewModel", factory)
            ShopDetailScreen(navController, shopDetailViewModel)
        }

    }
}

@ExperimentalMaterialApi
@ExperimentalCoroutinesApi
@Composable
fun ShopListScreenContent(
    shopListViewModel: ShopListViewModel,
    navController: NavController,
    isNetworkAvailable: Boolean,
) {

    val shopList = shopListViewModel.shopList.value
    val loading = shopListViewModel.loading.value
    val scrollState = rememberScrollState()
    val dialogQueue = shopListViewModel.dialogQueue
    val scaffoldState = rememberScaffoldState()

    AppTheme(
        displayProgressBar = loading,
        scaffoldState = scaffoldState,
        isNetworkAvailable = isNetworkAvailable,
        dialogQueue = dialogQueue.queue.value,
    ) {
        Scaffold {
            Box(
                modifier = Modifier.background(color = MaterialTheme.colors.surface)
            ) {
                if (loading) {
                    LoadingListShimmer(imageHeight = 250.dp)
                } else {
                    //ShopsLazyColumn(shopList, navController)
                    ShopsGrid(shopList, navController)
                }
            }
        }
    }
}

@ExperimentalCoroutinesApi
@Composable
fun ShopsLazyColumn(shopList: List<Shop>, navController: NavController) {
    LazyColumn {
        itemsIndexed(
            items = shopList,
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


@ExperimentalCoroutinesApi
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ShopsGrid(shops: List<Shop>, navController: NavController) {
    LazyVerticalGrid(
        cells = GridCells.Fixed(2)
    ) {
        items(shops) { shop ->
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
