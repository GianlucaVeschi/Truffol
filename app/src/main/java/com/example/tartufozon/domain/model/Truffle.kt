package com.example.tartufozon.domain.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

// TODO: 28.12.20 : Write a mapper
@Parcelize
data class Truffle(
    @SerializedName("id")           val id: Int,
    @SerializedName("title")        val tartufoName: String?,
    @SerializedName("description")  val description: String?,
    @SerializedName("image_url")    val image_url: String?,
    @SerializedName("rating")       val rating: Long?
) : Parcelable