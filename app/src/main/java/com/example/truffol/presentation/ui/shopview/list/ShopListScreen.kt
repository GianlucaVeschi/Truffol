package com.example.truffol.presentation.ui.shopview.list

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.HiltViewModelFactory
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
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
import timber.log.Timber

@ExperimentalComposeUiApi
@ExperimentalFoundationApi
@ExperimentalMaterialApi
@ExperimentalCoroutinesApi
@Composable
fun ShopListScreen(
    shopListViewModel: ShopListViewModel,
    shopDetailViewModel: ShopDetailViewModel,
    isNetworkAvailable: Boolean,
) {
    //Each NavController must be associated with a single NavHost composable.
    val navHostController: NavHostController = rememberNavController()

    NavHost(navHostController, startDestination = Screens.ShopListScreen.route) {

        composable(Screens.ShopListScreen.route) {
            ShopListScreenContent(shopListViewModel, navHostController, isNetworkAvailable)
        }

        composable(
            route = DetailScreens.ShopDetailScreen.route + "/{shopId}",
            arguments = listOf(navArgument("shopId") {
                type = NavType.IntType
            })
        ) {
            ShopDetailScreen(
                shopDetailViewModel,
                it.arguments?.getInt("shopId"),
            )
        }

    }
}

@ExperimentalComposeUiApi
@ExperimentalFoundationApi
@ExperimentalMaterialApi
@ExperimentalCoroutinesApi
@Composable
fun ShopListScreenContent(
    shopListViewModel: ShopListViewModel,
    navHostController: NavHostController,
    isNetworkAvailable: Boolean,
) {

    val shopList = shopListViewModel.shopList.value
    val loading = shopListViewModel.loading.value
    val scrollState = rememberScrollState()
    val dialogQueue = shopListViewModel.dialogQueue
    val scaffoldState = rememberScaffoldState()

    AppTheme(
        displayProgressBar = loading,
        darkTheme = false, //TODO(Add Toggle)
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
                    //ShopsLazyColumn(shopList, navHostController)
                    ShopsGrid(shopList, navHostController)
                }
            }
        }
    }
}

@ExperimentalMaterialApi
@ExperimentalCoroutinesApi
@Composable
fun ShopsLazyColumn(shopList: List<Shop>, navController: NavHostController) {
    LazyColumn {
        itemsIndexed(
            items = shopList,
        ) { index, shop ->
            ShopCard(shop, onClickCard = {
                val route = DetailScreens.ShopDetailScreen.route + "/${shop.shopId}"
                navController.navigate(route)
            })
        }
    }
}


@ExperimentalMaterialApi
@ExperimentalFoundationApi
@ExperimentalCoroutinesApi
@Composable
fun ShopsGrid(shops: List<Shop>, navController: NavHostController) {
    LazyVerticalGrid(
        cells = GridCells.Fixed(2)
    ) {
        items(shops) { shop ->
            ShopCard(shop, onClickCard = {
                val route = DetailScreens.ShopDetailScreen.route + "/${shop.shopId}"
                navController.navigate(route)
            })
        }
    }
}
