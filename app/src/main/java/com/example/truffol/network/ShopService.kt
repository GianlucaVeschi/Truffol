package com.example.truffol.network

import com.example.truffol.network.model.ShopDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ShopService {

    @GET("negozi/")
    suspend fun getShopList(): Response<List<ShopDto>>

    @GET("negozi/{id}")
    suspend fun getShopDetail(@Path("id") id: Int): Response<ShopDto>

}
