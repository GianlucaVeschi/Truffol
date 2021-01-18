package com.example.tartufozon.network

import com.example.tartufozon.domain.model.Truffle
import com.example.tartufozon.network.model.TruffleListDto
import timber.log.Timber
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val truffleService: TruffleService
) : BaseDataSource() {

    suspend fun getTruffleDetail(): Truffle {
        val resourceTruffleDetail = getResult { truffleService.getTruffleDetail() }
        return resourceTruffleDetail.data!!
    }

    suspend fun getTruffleList(): TruffleListDto {
        val resourceTruffleList = getResult { truffleService.getTruffleList() }
        Timber.d("response ${resourceTruffleList.data!!}")
        return resourceTruffleList.data!!
    }
}