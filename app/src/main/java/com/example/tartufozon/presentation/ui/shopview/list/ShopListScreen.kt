package com.example.tartufozon.presentation.ui.shopview.list

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.tartufozon.domain.model.Shop
import com.example.tartufozon.presentation.components.CircularIndeterminateProgressBar
import com.example.tartufozon.presentation.components.ShopCard
import com.example.tartufozon.presentation.components.shimmer.LoadingListShimmer
import kotlinx.coroutines.ExperimentalCoroutinesApi
import timber.log.Timber

@ExperimentalCoroutinesApi
@Composable
fun ShopListScreen(
    shopListViewModel: ShopListViewModel
) {

    val shopList = shopListViewModel.shopList.value
    val loading = shopListViewModel.loading.value
    val scrollState = rememberScrollState()

    //val shopListViewModel : ShopListViewModel = viewModel() // Error

    Scaffold{
        BuildShopList(shops = shopList, isLoading = loading)
    }
}

@ExperimentalCoroutinesApi
@Composable
fun BuildShopList(shops: List<Shop>, isLoading: Boolean) {
    var click = 0
    Box(
        modifier = Modifier.background(color = MaterialTheme.colors.surface)
    ) {
        if (isLoading) {
            LoadingListShimmer(imageHeight = 250.dp)
        } else {
            LazyColumn {
                itemsIndexed(
                    items = shops
                ) { index, shop ->
                    ShopCard(shop) {
                        Timber.d("click shop $index")
                    }
                }
            }
        }
        CircularIndeterminateProgressBar(isDisplayed = isLoading, verticalBias = 0.5f)
    }
}

fun showMessage(context: Context, message:String){
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}