package com.example.truffol.cache

import com.example.truffol.db.ShopDao
import com.example.truffol.db.model.ShopEntity

class ShopDaoFake(
    private val appDatabaseFake: AppDatabaseFake
): ShopDao {

    override suspend fun insertShop(shop: ShopEntity): Long {
        appDatabaseFake.shops.add(shop)
        return 1 // return success
    }

    override suspend fun insertShops(shops: List<ShopEntity>): LongArray {
        appDatabaseFake.shops.addAll(shops)
        return longArrayOf(1) // return success
    }

    override suspend fun getAllShops(): List<ShopEntity> {
        return appDatabaseFake.shops
    }

    override suspend fun getShopById(id: Int): ShopEntity? {
        return appDatabaseFake.shops.find { it.id == id }
    }

    override suspend fun deleteShops(ids: List<Int>): Int {
        appDatabaseFake.shops.removeIf { it.id in ids }
        return 1 // return success
    }

    override suspend fun deleteAllShops() {
        appDatabaseFake.shops.clear()
    }

    override suspend fun deleteShop(primaryKey: Int): Int {
        appDatabaseFake.shops.removeIf { it.id == primaryKey }
        return 1 // return success
    }
}