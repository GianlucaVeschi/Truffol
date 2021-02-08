package com.example.tartufozon.domain.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

// TODO: 28.12.20 : Write a mapper
@Parcelize
data class Shop(
    @SerializedName("id")           val id: Int,
    @SerializedName("title")        val shopName: String,
    @SerializedName("description")  val description: String?,
    @SerializedName("image_url")    val image_url: String?,
    @SerializedName("website")      val website: String?,
    @SerializedName("location")     val location: String?,
    @SerializedName("email")        val email: String?,
    @SerializedName("phone")        val phone: String?
): Parcelable

