package com.example.tartufozon.network

import com.example.tartufozon.network.models.TartufoApiResponse
import retrofit2.Call
import retrofit2.http.GET

interface TartufoService {

    @GET("/list_of_tartufi")
    fun getTartufi(): Call<TartufoApiResponse>
}
