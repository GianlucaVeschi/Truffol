package com.example.tartufozon.presentation.ui.shopview.repo

import com.example.tartufozon.domain.model.Shop

interface ShopRepository {

    suspend fun getShopList() : List<Shop>

    suspend fun getShopDetail(shopId : Int): Shop

}
