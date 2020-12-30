package com.example.tartufozon.presentation.ui.repo

import com.example.tartufozon.domain.model.Truffle
import com.example.tartufozon.network.RemoteDataSource
import com.example.tartufozon.network.model.TruffleListDto
import javax.inject.Inject

class TruffleRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource
) : TruffleRepository {

    override suspend fun getTruffleDetail(): Truffle {
        return remoteDataSource.getTruffleDetail()
    }

    override suspend fun getTruffleList(): TruffleListDto {
        return remoteDataSource.getTruffleList()
    }
}
