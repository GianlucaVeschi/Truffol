package com.example.truffol.domain.model

import com.google.gson.annotations.SerializedName

data class Truffle(
    @SerializedName("id") val truffleId: Int,
    @SerializedName("title") val tartufoName: String,
    @SerializedName("description") val description: String,
    @SerializedName("image_url") val image_url: String,
    @SerializedName("rating") val rating: Long,
    @SerializedName("price") val price: Long
)