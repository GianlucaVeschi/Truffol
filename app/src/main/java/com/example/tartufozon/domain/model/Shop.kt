package com.example.tartufozon.domain.model

import com.google.gson.annotations.SerializedName

// TODO: 28.12.20 : Write a mapper
data class Shop(
    @SerializedName("id")           val id: Int,
    @SerializedName("title")        val shopName: String?,
    @SerializedName("description")  val description: String?,
    @SerializedName("image_url")    val image_url: String?,
    @SerializedName("rating")       val rating: Long?
)