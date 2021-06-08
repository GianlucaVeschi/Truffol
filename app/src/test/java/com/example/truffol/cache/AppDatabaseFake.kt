package com.example.truffol.cache

import com.example.truffol.db.model.ShopEntity
import com.example.truffol.db.model.TruffleEntity

class AppDatabaseFake {
    // fake tables in local db
    val truffles = mutableListOf<TruffleEntity>()
    val shops = mutableListOf<ShopEntity>()
}
