package com.example.tartufozon.presentation.ui.truffleview.repo

import com.example.tartufozon.domain.model.Truffle
import com.example.tartufozon.network.RemoteDataSource
import javax.inject.Inject

//Not used, here just for reference
class TruffleRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource
) : TruffleRepository {

    override suspend fun getTruffleList(): List<Truffle> {
        return remoteDataSource.getTruffleList()
    }

    override suspend fun getTruffleDetail(truffleId : Int): Truffle {
        return remoteDataSource.getTruffleDetail(truffleId)
    }
}
