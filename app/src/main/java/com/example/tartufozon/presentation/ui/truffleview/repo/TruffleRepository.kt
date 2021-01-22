package com.example.tartufozon.presentation.ui.truffleview.repo

import com.example.tartufozon.domain.model.Truffle

interface TruffleRepository {

    //HEROKU_DB
    suspend fun getLocalTruffleList() : List<Truffle>

    suspend fun getLocalTruffleDetail(truffleId : Int): Truffle
}