package com.example.truffol.db.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "shops")
data class ShopEntity(

    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    val id: Int,

    @ColumnInfo(name = "shopName")
    val shopName: String,

    @ColumnInfo(name = "description")
    val description: String,

    @ColumnInfo(name = "image_url")
    val image_url: String,

    @ColumnInfo(name = "website")
    val website: String,

    @ColumnInfo(name = "location")
    val location: String,

    @ColumnInfo(name = "email")
    val email: String,

    @ColumnInfo(name = "phone")
    val phone: String,

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






