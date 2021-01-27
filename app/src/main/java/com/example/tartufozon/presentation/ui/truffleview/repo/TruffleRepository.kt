package com.example.tartufozon.presentation.ui.truffleview.repo

import com.example.tartufozon.domain.model.Truffle

interface TruffleRepository {

    suspend fun getTruffleList() : List<Truffle>

    suspend fun getTruffleDetail(truffleId : Int): Truffle

}