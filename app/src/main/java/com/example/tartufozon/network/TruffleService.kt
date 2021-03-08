package com.example.tartufozon.network

import com.example.tartufozon.domain.model.Shop
import com.example.tartufozon.domain.model.Truffle
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface TruffleService {

    @GET("tartufi/")
    suspend fun getTruffleList(): Response<List<Truffle>>

    @GET("tartufi/{id}")
    suspend fun getTruffleDetail(@Path("id") id: Int): Response<Truffle>

//    @GET("negozi/")
//    suspend fun getShopList(): Response<List<Shop>>
//
//    @GET("negozi/{id}")
//    suspend fun getShopDetail(@Path("id") id: Int): Response<Shop>

}
