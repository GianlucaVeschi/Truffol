package com.example.tartufozon.network.models

data class TartufoApiResponse(
    val tartufi : List<Tartufo>
)

data class Tartufo (
    val id : Int,
    val title : String?,
    val description : String?
)