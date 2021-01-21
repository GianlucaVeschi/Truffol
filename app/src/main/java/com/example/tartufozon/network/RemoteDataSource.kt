package com.example.tartufozon.network

import com.example.tartufozon.domain.model.Truffle
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

    suspend fun getTruffleList(): List<Truffle> {
        var resourceTruffleList : Response<List<Truffle>>? = null
        try {
            //resourceTruffleList = getResult { truffleService.getTruffleList() } //error fix me
            resourceTruffleList = truffleService.getTruffleList()
        }
        catch (error : Error ){
            Timber.e(error)
        }
        return resourceTruffleList?.body()!!
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