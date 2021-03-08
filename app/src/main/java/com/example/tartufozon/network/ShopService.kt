package com.example.tartufozon.network

import com.example.tartufozon.domain.model.Shop
import com.example.tartufozon.domain.model.Truffle
import com.example.tartufozon.network.model.ShopDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ShopService {

    @GET("negozi/")
    suspend fun getShopList(): Response<List<ShopDto>>

    @GET("negozi/{id}")
    suspend fun getShopDetail(@Path("id") id: Int): Response<ShopDto>

}
