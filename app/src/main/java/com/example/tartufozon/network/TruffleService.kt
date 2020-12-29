package com.example.tartufozon.network

import com.example.tartufozon.network.model.TartufoApiResponse
import com.example.tartufozon.domain.model.Truffle
import retrofit2.Response
import retrofit2.http.GET

interface TruffleService {

    @GET("single_tartufo")
    suspend fun getTartufo(): Response<Truffle>

    @GET("list_of_tartufi")
    suspend fun getTartufi(): Response<TartufoApiResponse>
}
