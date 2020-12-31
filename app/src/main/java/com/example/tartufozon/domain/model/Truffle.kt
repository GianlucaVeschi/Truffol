package com.example.tartufozon.domain.model

import com.google.gson.annotations.SerializedName

// TODO: 28.12.20 : Write a mapper
data class Truffle(
    @SerializedName("id")
    val id: Int,
    @SerializedName("title")
    val tartufoName: String?,
    @SerializedName("description")
    val description: String?,
    @SerializedName("rating")
    val rating: Long?
)