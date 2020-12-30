package com.example.tartufozon.network

import com.example.tartufozon.domain.model.Truffle

import com.example.tartufozon.network.model.TruffleDto
import retrofit2.Response
import retrofit2.http.GET

interface TruffleService {

    @GET("single_tartufo")
    suspend fun getTruffleDetail(): Response<Truffle>

    @GET("list_of_tartufi")
    suspend fun getTruffleList(): Response<TruffleDto>
}
