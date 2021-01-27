package com.example.tartufozon.network

import com.example.tartufozon.domain.model.Shop
import com.example.tartufozon.domain.model.Truffle
import retrofit2.Response
import timber.log.Timber
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val truffleService: TruffleService,
) : BaseDataSource() {

    suspend fun getTruffleList() : List<Truffle> {
        var truffleList : Response<List<Truffle>>? = null
        try {
            truffleList = truffleService.getTruffleList()
        }
        catch (error : Error ){
            Timber.e(error)
        }
        return truffleList?.body()!!
    }

    suspend fun getTruffleDetail(truffleId : Int): Truffle {
        var truffleDetail : Response<Truffle>? = null
        try {
            truffleDetail = truffleService.getTruffleDetail(truffleId)
        }
        catch (error : Error ){
            Timber.e(error)
        }
        return truffleDetail?.body()!!
    }

    suspend fun getShopList(): List<Shop> {
        var shopList : Response<List<Shop>>? = null
        try {
            shopList = truffleService.getShopList()
        }
        catch (error : Error){
            Timber.e(error)
        }
        return shopList?.body()!!
    }

    suspend fun getShopDetail(shopId: Int): Shop {
        var shopDetail : Response<Shop>? = null
        try {
            shopDetail = truffleService.getShopDetail(shopId)
        }
        catch (error : Error){
            Timber.e(error)
        }
        return shopDetail?.body()!!
    }

}