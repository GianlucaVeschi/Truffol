package com.example.tartufozon.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.tartufozon.db.model.ShopEntity
import com.example.tartufozon.db.model.TruffleEntity

@Database(entities = [ShopEntity::class, TruffleEntity::class], version = 2)
abstract class AppDatabase : RoomDatabase() {

    abstract fun shopDao(): ShopDao
    abstract fun truffleDao(): TruffleDao

    companion object {
        val DATABASE_NAME: String = "truffle_app_db"
    }

}