package com.example.tartufozon.presentation.ui.shopview.list

sealed class ShopListEvent {

    //Note that it would make more sense to use a class if we were passing smth to the event
    object GetShopList : ShopListEvent()
}