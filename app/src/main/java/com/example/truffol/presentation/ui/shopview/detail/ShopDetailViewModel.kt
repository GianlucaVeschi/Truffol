package com.example.truffol.presentation.ui.shopview.detail

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.truffol.domain.model.Shop
import com.example.truffol.interactors.shop.GetShopUseCase
import com.example.truffol.presentation.ui.shopview.repo.ShopRepositoryImpl
import com.example.truffol.presentation.ui.util.DialogQueue
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import timber.log.Timber
import javax.inject.Inject

const val STATE_KEY_SHOP = "shop.state.shop.key"

@HiltViewModel
class ShopDetailViewModel @Inject constructor(
    private val ShopRepositoryImpl: ShopRepositoryImpl,
    private val state: SavedStateHandle,
    private val getShopUseCase: GetShopUseCase
) : ViewModel() {

    val shop: MutableState<Shop?> = mutableStateOf(null)
    val loading = mutableStateOf(false)
    val dialogQueue = DialogQueue()

    init {
        // restore if process dies
        state.get<Int>(STATE_KEY_SHOP)?.let { ShopId ->
            onTriggerEvent(ShopDetailEvent.getShopDetailEvent(ShopId))
        }
    }

    fun onTriggerEvent(detailEvent: ShopDetailEvent) {
        try {
            when (detailEvent) {
                //UseCase #1
                is ShopDetailEvent.getShopDetailEvent -> {
                    getShopDetailUseCase(detailEvent.id)
                }
            }
        } catch (e: Exception) {
            Timber.e("launchJob: Exception: ${e}, ${e.cause}")
            e.printStackTrace()
        }
    }

    private fun getShopDetailUseCase(shopId: Int) {
        loading.value = true

        getShopUseCase.run(shopId).onEach { dataState ->
            loading.value = dataState.loading

            dataState.data?.let {
                Timber.d(it.toString())
                shop.value = it
            }

            dataState.error?.let { error ->
                Timber.e("newSearch: ${error}")
                dialogQueue.appendErrorMessage("An Error Occurred", error)
            }
        }.launchIn(viewModelScope)


        Timber.d(shop.toString())

        loading.value = false
    }

}