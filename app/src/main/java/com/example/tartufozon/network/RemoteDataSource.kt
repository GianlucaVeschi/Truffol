package com.example.tartufozon.network

import com.example.tartufozon.domain.model.Truffle
import com.example.tartufozon.network.model.TruffleListDto
import retrofit2.Response
import timber.log.Timber
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val truffleService: TruffleService,
    private val localTruffleService: LocalTruffleService
) : BaseDataSource() {

    suspend fun getTruffleDetail(): Truffle {
        val resourceTruffleDetail = getResult { truffleService.getTruffleDetail() }
        return resourceTruffleDetail.data!!
    }

    suspend fun getTruffleList(): TruffleListDto {
        val resourceTruffleList = getResult { truffleService.getTruffleList() }
        return resourceTruffleList.data!!
    }

    //LOCAL DB
    suspend fun getLocalTruffleList() : List<Truffle> {
        var resourceLocalTruffleList : Response<List<Truffle>>? = null
        try {
            resourceLocalTruffleList = localTruffleService.getLocalTruffleList()
        }
        catch (error : Error ){
            Timber.e(error)
        }
        return resourceLocalTruffleList?.body()!!
    }

    suspend fun getLocalTruffleDetail(truffleId : Int): Truffle {
        var resourceLocalTruffleDetail : Response<Truffle>? = null
        try {
            resourceLocalTruffleDetail = localTruffleService.getLocalTruffleDetail(truffleId)
        }
        catch (error : Error ){
            Timber.e(error)
        }
        return resourceLocalTruffleDetail?.body()!!
    }

}