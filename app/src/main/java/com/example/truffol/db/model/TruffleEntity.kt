package com.example.truffol.db.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "truffles")
data class TruffleEntity(

    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    val id: Int,

    @ColumnInfo(name = "title")
    val tartufoName: String,

    @ColumnInfo(name = "description")
    val description: String,

    @ColumnInfo(name = "image_url")
    val image_url: String,

    @ColumnInfo(name = "rating")
    val rating: Long,

    @ColumnInfo(name = "price")
    val price: Long

    /** NOT USED YET */

    //Value from API
    //@ColumnInfo(name = "date_added")
    //var dateAdded: Long,

    //Value from API
    //@ColumnInfo(name = "date_updated")
    //var dateUpdated: Long,

    //The date this recipe was "refreshed" in the cache.
    //@ColumnInfo(name = "date_cached")
    //var dateCached: Long
)






