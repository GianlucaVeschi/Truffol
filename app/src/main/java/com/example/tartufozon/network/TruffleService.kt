package com.example.tartufozon.network

import com.example.tartufozon.domain.model.Truffle

import com.example.tartufozon.network.model.TruffleListDto
import retrofit2.Response
import retrofit2.http.GET

interface TruffleService {

    @GET("single_tartufo") //POSTMAN_DB
    //@GET("tartufi") //LOCAL_DB
    suspend fun getTruffleDetail(): Response<Truffle>


    @GET("list_of_tartufi") //POSTMAN_DB
    //@GET("tartufi/") //LOCAL_DB
    suspend fun getTruffleList(): Response<TruffleListDto>
}
