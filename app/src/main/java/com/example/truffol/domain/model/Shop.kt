package com.example.truffol.domain.model

import com.google.gson.annotations.SerializedName

data class Shop(
    @SerializedName("id") val shopId: Int,
    @SerializedName("title") val shopName: String,
    @SerializedName("description") val description: String,
    @SerializedName("image_url") val image_url: String,
    @SerializedName("website") val website: String,
    @SerializedName("location") val location: String,
    @SerializedName("email") val email: String,
    @SerializedName("phone") val phone: String
    // TODO: 23.02.21 : Add Dates
    //val dateAdded: Date,
    //val dateUpdated: Date,
)
