package com.example.tartufozon.network

import com.example.tartufozon.network.models.Tartufo
import com.example.tartufozon.network.models.TartufoApiResponse
import retrofit2.Response
import retrofit2.http.GET

interface TartufoService {

    @GET("/single_tartufo")
    suspend fun getTartufo(): Response<Tartufo>

    @GET("/list_of_tartufi")
    suspend fun getTartufi(): Response<TartufoApiResponse>
}
