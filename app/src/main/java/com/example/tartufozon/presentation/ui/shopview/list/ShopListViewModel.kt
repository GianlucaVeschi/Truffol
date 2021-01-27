package com.example.tartufozon.presentation.ui.shopview.list

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tartufozon.domain.model.Shop
import com.example.tartufozon.presentation.ui.shopview.repo.ShopRepositoryImpl
import kotlinx.coroutines.launch
import timber.log.Timber

class ShopListViewModel @ViewModelInject constructor(
    private val shopRepositoryImpl: ShopRepositoryImpl
) : ViewModel() {

    val shopList: MutableState<List<Shop>> = mutableStateOf(ArrayList())
    //var categoryScrollPosition: Float = 0f
    val loading = mutableStateOf(false)

    fun triggerEvent(){
        viewModelScope.launch {
            try {
                getShopList()
                printShops()
            } catch (e: Exception) {
                Timber.e("launchJob: Exception: ${e}, ${e.cause}")
                e.printStackTrace()
            } finally {
                Timber.d("launchJob: finally called.")
            }
        }
    }

    private suspend fun getShopList() {
        //resetSearchState()
        //loading.value = true

        val tmpShopList = shopRepositoryImpl.getShopList()
        shopList.value = tmpShopList
        //loading.value = false
    }

    private fun printShops(){
        for(shop in shopList.value){
            Timber.d("shop : $shop")
        }
    }

}