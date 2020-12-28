package com.example.tartufozon.network

import com.example.tartufozon.network.models.TartufoApiResponse
import com.example.tartufozon.network.models.Truffle
import retrofit2.Response
import retrofit2.http.GET

interface TartufoService {

    @GET("single_tartufo")
    suspend fun getTartufo(): Response<Truffle>

    @GET("list_of_tartufi")
    suspend fun getTartufi(): Response<TartufoApiResponse>
}
