package com.example.tartufozon.db

import androidx.room.Dao
import androidx.room.Insert

@Dao
interface ShopDao {

    @Insert
    suspend fun insertShop(shop: ShopEntity): Long

}