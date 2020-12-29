package com.example.tartufozon.presentation.ui.repo

import com.example.tartufozon.network.TruffleService
import com.example.tartufozon.network.model.TartufoApiResponse
import com.example.tartufozon.domain.model.Truffle

class TruffleRepositoryImpl(
    private val truffleService: TruffleService
) : TruffleRepository {

    override suspend fun getTruffle(): Truffle {
       return truffleService.getTartufo().body()!!
    }

    override suspend fun getTruffles(): TartufoApiResponse{
        return truffleService.getTartufi().body()!!
    }
}
