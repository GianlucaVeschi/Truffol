package com.example.tartufozon.presentation.ui.shopview.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.tartufozon.R
import com.example.tartufozon.domain.model.Shop
import com.example.tartufozon.presentation.components.ShopDetailView
import com.example.tartufozon.presentation.ui.truffleview.detail.TruffleDetailViewModel
import com.example.tartufozon.util.Constants.SHOP_KEY
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@Composable
fun ShopDetailScreen(
    navController: NavController,
    shopDetailViewModel: ShopDetailViewModel
){
    val shopDetail = navController.previousBackStackEntry?.arguments?.getInt(SHOP_KEY)!!
    shopDetail.let {

        shopDetailViewModel.onTriggerEvent(
            ShopDetailEvent.getShopDetailEvent(it)
        )

        val scaffoldState = rememberScaffoldState()
        Scaffold(
            scaffoldState = scaffoldState,
            snackbarHost = {
                scaffoldState.snackbarHostState
            }
        ) {
            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                shopDetailViewModel.shop.value?.let { shopDetail ->
                    ShopDetailView(
                        shop = shopDetail
                    )
                }
            }
        }
    }
}