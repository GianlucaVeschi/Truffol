package com.example.truffol.presentation.ui.shopview.repo

import com.example.truffol.domain.model.Shop
import com.example.truffol.network.RemoteDataSource
import javax.inject.Inject

//Not used, here just for reference
class ShopRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource
) : ShopRepository {

    override suspend fun getShopList(): List<Shop> {
        return remoteDataSource.getShopList()
    }

    override suspend fun getShopDetail(shopId : Int): Shop {
        return remoteDataSource.getShopDetail(shopId)
    }
}
