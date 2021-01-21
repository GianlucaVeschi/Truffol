package com.example.tartufozon.presentation.ui.repo

import com.example.tartufozon.domain.model.Truffle

interface TruffleRepository {

    suspend fun getTruffleDetail(): Truffle

    suspend fun getTruffleList(): List<Truffle>

    //LOCAL_DB
    suspend fun getLocalTruffleList() : List<Truffle>

    suspend fun getLocalTruffleDetail(truffleId : Int): Truffle
}