package com.example.tartufozon.presentation.ui.shopview.list

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tartufozon.domain.model.Shop
import com.example.tartufozon.interactors.SearchShopsUseCase
import com.example.tartufozon.presentation.ui.shopview.repo.ShopRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ShopListViewModel @Inject constructor(
    private val searchShopsUseCase: SearchShopsUseCase,
    private val shopRepositoryImpl: ShopRepositoryImpl
) : ViewModel() {

    val shopList: MutableState<List<Shop>> = mutableStateOf(ArrayList())
    //var categoryScrollPosition: Float = 0f
    val loading = mutableStateOf(false)

    init {
        triggerEvent(ShopListEvent.GetShopList)
    }

    private fun triggerEvent(event : ShopListEvent){
        viewModelScope.launch {
            try {
                when(event){
                    is ShopListEvent.GetShopList -> {
                        getShopListUseCase()
                    }
                }
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
        loading.value = true
        val tmpShopList = shopRepositoryImpl.getShopList()
        shopList.value = tmpShopList
        loading.value = false
    }

    private fun getShopListUseCase() {
        searchShopsUseCase.run().onEach { dataState ->
            loading.value = dataState.loading

            dataState.data?.let { list ->
                shopList.value = list
            }

            dataState.error?.let { error ->
                Timber.e("newSearch: ${error}")
                // TODO("Handle error")
            }
        }.launchIn(viewModelScope)
    }

    private fun printShops(){
        for(shop in shopList.value){
            Timber.d("shop : $shop")
        }
    }

}