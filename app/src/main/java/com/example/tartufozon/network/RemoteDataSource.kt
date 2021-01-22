package com.example.tartufozon.network

import com.example.tartufozon.domain.model.Truffle
import retrofit2.Response
import timber.log.Timber
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val truffleService: TruffleService
) : BaseDataSource() {

    //HEROKU DB
    suspend fun getLocalTruffleList() : List<Truffle> {
        var resourceLocalTruffleList : Response<List<Truffle>>? = null
        try {
            resourceLocalTruffleList = truffleService.getLocalTruffleList()
        }
        catch (error : Error ){
            Timber.e(error)
        }
        return resourceLocalTruffleList?.body()!!
    }

    suspend fun getLocalTruffleDetail(truffleId : Int): Truffle {
        var resourceLocalTruffleDetail : Response<Truffle>? = null
        try {
            resourceLocalTruffleDetail = truffleService.getLocalTruffleDetail(truffleId)
        }
        catch (error : Error ){
            Timber.e(error)
        }
        return resourceLocalTruffleDetail?.body()!!
    }

}