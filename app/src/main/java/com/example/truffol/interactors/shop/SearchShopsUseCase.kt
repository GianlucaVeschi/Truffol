package com.example.truffol.interactors.shop

import com.example.truffol.db.ShopDao
import com.example.truffol.db.model.ShopEntityMapper
import com.example.truffol.domain.model.Shop
import com.example.truffol.domain.util.DataState
import com.example.truffol.network.ShopService
import com.example.truffol.network.model.ShopDtoMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import timber.log.Timber

class SearchShopsUseCase(
    private val shopDao: ShopDao,
    private val shopService: ShopService,
    private val entityMapper: ShopEntityMapper,
    private val dtoMapper: ShopDtoMapper
) {

    fun run(): Flow<DataState<List<Shop>>> = flow {
        emit(DataState.loading())

        //Try to get data from the cache
        val shopListFromCache = getShopsListFromCache()

        //If Data is not in the cache then get it from the network
        if (shopListFromCache.data.isNullOrEmpty()) {
            val shopsListFromNetwork: DataState<List<Shop>> = getShopsListFromNetwork()
            shopsListFromNetwork.data?.let {
                shopDao.insertShops(entityMapper.toEntityList(it))
            }
        }

        //Finally emit data from the cache
        emit(getShopsListFromCache())
    }

    private suspend fun getShopsListFromCache(): DataState<List<Shop>> = try {
        Timber.d("Get Shops from the cache")
        val shops = shopDao.getAllShops()
        DataState(entityMapper.fromEntityList(shops))
    } catch (exception: Exception) {
        DataState.error(exception.message ?: "Unknown Error")
    }

    // WARNING: This will throw exception if there is no network connection
    private suspend fun getShopsListFromNetwork(): DataState<List<Shop>> = try {
        Timber.d("Get Shops from the network")
        val response = shopService.getShopList()
        response.takeIf { it.isSuccessful }?.body()?.let {
            DataState(dtoMapper.toDomainList(it))
        } ?: DataState.error(response.message() ?: "Unknown Error")
    } catch (exception: Exception) {
        DataState.error(exception.message ?: "Unknown Error")
    }


}