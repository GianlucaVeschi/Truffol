package com.example.truffol.presentation.ui.shopview.repo

import com.example.truffol.domain.model.Shop

//Not used, here just for reference
interface ShopRepository {

    suspend fun getShopList() : List<Shop>

    suspend fun getShopDetail(shopId : Int): Shop

}
