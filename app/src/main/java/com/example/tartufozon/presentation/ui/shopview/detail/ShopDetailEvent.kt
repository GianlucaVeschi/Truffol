package com.example.tartufozon.presentation.ui.shopview.detail

sealed class ShopDetailEvent{

    data class getShopDetailEvent(
        val id: Int
    ): ShopDetailEvent()

}