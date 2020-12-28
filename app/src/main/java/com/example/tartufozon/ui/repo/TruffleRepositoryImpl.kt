package com.example.tartufozon.ui.repo

import com.example.tartufozon.network.TartufoService
import com.example.tartufozon.network.models.TartufoApiResponse
import com.example.tartufozon.network.models.Truffle

class TruffleRepositoryImpl(
    private val tartufoService: TartufoService
) : TruffleRepository {

    override suspend fun getTruffle(): Truffle {
       return tartufoService.getTartufo().body()!!
    }

    override suspend fun getTruffles(): TartufoApiResponse{
        return tartufoService.getTartufi().body()!!
    }
}
