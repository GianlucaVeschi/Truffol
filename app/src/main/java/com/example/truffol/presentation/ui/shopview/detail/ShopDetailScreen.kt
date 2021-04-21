package com.example.truffol.presentation.ui.shopview.detail

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.truffol.presentation.components.ShopDetailView
import com.example.truffol.util.Constants.SHOP_KEY
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@Composable
fun ShopDetailScreen(
    navController: NavController,
    shopDetailViewModel: ShopDetailViewModel
) {
    val shopDetail = navController.previousBackStackEntry?.arguments?.getInt(SHOP_KEY)!!
    shopDetail.let {

        shopDetailViewModel.onTriggerEvent(
            ShopDetailEvent.getShopDetailEvent(it)
        )

        shopDetailViewModel.shop.value?.let { shopDetail ->
            ShopDetailView(
                shop = shopDetail
            )
        }

    }
}