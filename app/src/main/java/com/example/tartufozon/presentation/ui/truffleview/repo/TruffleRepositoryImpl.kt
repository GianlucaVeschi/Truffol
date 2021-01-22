package com.example.tartufozon.presentation.ui.truffleview.repo

import com.example.tartufozon.domain.model.Truffle
import com.example.tartufozon.network.RemoteDataSource
import javax.inject.Inject

class TruffleRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource
) : TruffleRepository {

    override suspend fun getLocalTruffleList(): List<Truffle> {
        return remoteDataSource.getLocalTruffleList()
    }

    override suspend fun getLocalTruffleDetail(truffleId : Int): Truffle {
        return remoteDataSource.getLocalTruffleDetail(truffleId)
    }

}
