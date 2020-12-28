package com.example.tartufozon.network.models

data class TartufoApiResponse(
    val tartufi : List<Tartufo>
)

data class Tartufo (
    val tartufoTitle : String,
    val tartufoDescription : String
)