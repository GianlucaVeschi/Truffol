package com.example.tartufozon.presentation.ui.shopview.repo

import com.example.tartufozon.domain.model.Shop
import com.example.tartufozon.network.RemoteDataSource
import javax.inject.Inject

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
