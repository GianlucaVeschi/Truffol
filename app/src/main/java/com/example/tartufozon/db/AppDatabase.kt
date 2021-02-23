package com.example.tartufozon.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [ShopEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract  fun shopDao(): ShopDao

    companion object{
        val DATABASE_NAME: String = "shop_db"
    }

}