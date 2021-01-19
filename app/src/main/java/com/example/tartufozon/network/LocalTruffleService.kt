package com.example.tartufozon.network

import com.example.tartufozon.domain.model.Truffle
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface LocalTruffleService {

    @GET("tartufi/") //LOCAL_DB
    suspend fun getLocalTruffleList(): Response<List<Truffle>>

    @GET("tartufi/{id}")
    suspend fun getLocalTruffleDetail(@Path("id") id: Int): Response<Truffle>

}
