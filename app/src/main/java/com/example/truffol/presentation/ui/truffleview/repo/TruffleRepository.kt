package com.example.truffol.presentation.ui.truffleview.repo

import com.example.truffol.domain.model.Truffle

//Not used, here just for reference
interface TruffleRepository {

    suspend fun getTruffleList() : List<Truffle>

    suspend fun getTruffleDetail(truffleId : Int): Truffle

}