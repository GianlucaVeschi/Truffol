package com.example.truffol.cache

import com.example.truffol.db.TruffleDao
import com.example.truffol.db.model.TruffleEntity

class TruffleDaoFake(
    private val appDatabaseFake: AppDatabaseFake
): TruffleDao {

    override suspend fun insertTruffle(truffle: TruffleEntity): Long {
        appDatabaseFake.truffles.add(truffle)
        return 1 // return success
    }

    override suspend fun insertTruffles(truffles: List<TruffleEntity>): LongArray {
        appDatabaseFake.truffles.addAll(truffles)
        return longArrayOf(1) // return success
    }

    override suspend fun getAllTruffles(): List<TruffleEntity> {
        return appDatabaseFake.truffles
    }

    override suspend fun getTruffleById(id: Int): TruffleEntity? {
        return appDatabaseFake.truffles.find { it.id == id }
    }

    override suspend fun deleteTruffles(ids: List<Int>): Int {
        appDatabaseFake.truffles.removeIf { it.id in ids }
        return 1 // return success
    }

    override suspend fun deleteAllTruffles() {
        appDatabaseFake.truffles.clear()
    }

    override suspend fun deleteTruffle(primaryKey: Int): Int {
        appDatabaseFake.truffles.removeIf { it.id == primaryKey }
        return 1 // return success
    }
}