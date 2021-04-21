package com.example.truffol.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.truffol.db.model.TruffleEntity

@Dao
interface TruffleDao {

    @Insert
    suspend fun insertTruffle(truffle: TruffleEntity): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertTruffles(Truffles: List<TruffleEntity>): LongArray

    @Query("SELECT * FROM Truffles")
    suspend fun getAllTruffles(): List<TruffleEntity>

    @Query("SELECT * FROM Truffles WHERE id = :id")
    suspend fun getTruffleById(id: Int): TruffleEntity?

    @Query("DELETE FROM Truffles WHERE id IN (:ids)")
    suspend fun deleteTruffles(ids: List<Int>): Int

    @Query("DELETE FROM Truffles")
    suspend fun deleteAllTruffles()

    @Query("DELETE FROM Truffles WHERE id = :primaryKey")
    suspend fun deleteTruffle(primaryKey: Int): Int

}