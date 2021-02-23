package com.example.tartufozon.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.tartufozon.db.model.ShopEntity

@Dao
interface ShopDao {

    @Insert
    suspend fun insertShop(shop: ShopEntity): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertShops(shops: List<ShopEntity>): LongArray

    @Query("SELECT * FROM shops WHERE id = :id")
    suspend fun getShopById(id: Int): ShopEntity?

    @Query("DELETE FROM shops WHERE id IN (:ids)")
    suspend fun deleteShops(ids: List<Int>): Int

    @Query("DELETE FROM shops")
    suspend fun deleteAllShops()

    @Query("DELETE FROM shops WHERE id = :primaryKey")
    suspend fun deleteShop(primaryKey: Int): Int

    //Additional methods available at ...
    //https://github.com/mitchtabian/food2fork-compose/blob/room-dao-functions/app/src/main/java/com/codingwithmitch/food2forkcompose/cache/RecipeDao.kt
}