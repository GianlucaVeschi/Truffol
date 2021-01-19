package com.example.tartufozon.presentation.ui.repo

import com.example.tartufozon.domain.model.Truffle
import com.example.tartufozon.network.RemoteDataSource
import com.example.tartufozon.network.model.TruffleListDto
import javax.inject.Inject

class TruffleRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource
) : TruffleRepository {

    // TODO: 17.01.21 : Pass specific ID
    override suspend fun getTruffleDetail(): Truffle {
        return remoteDataSource.getTruffleDetail()
    }

    override suspend fun getTruffleList(): TruffleListDto {
        return remoteDataSource.getTruffleList()
    }

    override suspend fun getLocalTruffleList(): List<Truffle> {
        return remoteDataSource.getLocalTruffleList()
    }

    override suspend fun getLocalTruffleDetail(truffleId : Int): Truffle {
        return remoteDataSource.getLocalTruffleDetail(truffleId)
    }

}
