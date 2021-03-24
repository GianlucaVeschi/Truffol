package com.example.tartufozon.cache

import com.example.tartufozon.db.model.TruffleEntity

class AppDatabaseFake {
    // fake for truffle table in local db
    val truffles = mutableListOf<TruffleEntity>()
}
