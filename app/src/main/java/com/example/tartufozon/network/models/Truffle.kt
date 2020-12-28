package com.example.tartufozon.network.models

import com.google.gson.annotations.SerializedName

// TODO: 28.12.20 : Write a mapper
data class Truffle (
    val id : Int,
    @SerializedName("title")
    val tartufoName : String?,
    @SerializedName("description")
    val description : String?
)