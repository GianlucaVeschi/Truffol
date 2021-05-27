package com.example.truffol.presentation.ui.shopview.detail

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.truffol.presentation.components.ShopDetailView
import com.example.truffol.util.Constants.SHOP_KEY
import kotlinx.coroutines.ExperimentalCoroutinesApi
import timber.log.Timber

@ExperimentalCoroutinesApi
@Composable
fun ShopDetailScreen(
    //navController: NavController,
    shopDetailViewModel: ShopDetailViewModel,
    shopId: Int?,
) {

    //val shopDetail = navController.currentBackStackEntry?.arguments?.getInt(SHOP_KEY)
    //Timber.d(shopDetail.toString())

    shopId?.let {
        shopDetailViewModel.onTriggerEvent(
            ShopDetailEvent.getShopDetailEvent(it)
        )

        shopDetailViewModel.shop.value?.let { shopDetail ->
            ShopDetailView(shop = shopDetail)
        }
    }
}