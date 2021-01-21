package com.example.tartufozon.network

import com.example.tartufozon.domain.model.Truffle
import retrofit2.Response
import retrofit2.http.GET

interface TruffleService {

    @GET("single_tartufo") //POSTMAN_DB
    //@GET("tartufi") //LOCAL_DB
    suspend fun getTruffleDetail(): Response<Truffle>


    @GET("tartufi/") //HEROKU_DB
    suspend fun getTruffleList(): Response<List<Truffle>>
}
