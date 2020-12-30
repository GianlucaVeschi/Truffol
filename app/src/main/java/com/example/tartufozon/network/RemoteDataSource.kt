package com.example.tartufozon.network

import com.example.tartufozon.domain.model.Truffle
import com.example.tartufozon.network.model.TruffleDto
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val truffleService: TruffleService
) {
    suspend fun getTruffleDetail(): Truffle {
        return truffleService.getTruffleDetail().body()!!
    }

    suspend fun getTruffleList(): TruffleDto {
        return truffleService.getTruffleList().body()!!
    }
}