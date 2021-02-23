package com.example.tartufozon.network.model

import com.example.tartufozon.domain.model.Truffle
import com.google.gson.annotations.SerializedName

// TODO: 30.12.20 : Improve DTO Model 
data class ShopDto(
    @SerializedName("id")           val id: Int,
    @SerializedName("title")        val shopName: String,
    @SerializedName("description")  val description: String,
    @SerializedName("image_url")    val image_url: String,
    @SerializedName("website")      val website: String,
    @SerializedName("location")     val location: String,
    @SerializedName("email")        val email: String,
    @SerializedName("phone")        val phone: String
)