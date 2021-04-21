package com.example.truffol.cache

import com.example.truffol.db.model.TruffleEntity

class AppDatabaseFake {
    // fake for truffle table in local db
    val truffles = mutableListOf<TruffleEntity>()
}
