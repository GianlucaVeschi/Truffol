package com.example.truffol.interactors.shop

import com.example.truffol.db.ShopDao
import com.example.truffol.db.model.ShopEntityMapper
import com.example.truffol.domain.model.Shop
import com.example.truffol.domain.model.Truffle
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
        val shopsListFromCache = getShopsListFromCache()

        //If Data is not in the cache then get it from the network and save it in the cache
        if (shopsListFromCache.data.isNullOrEmpty()) {
            val trufflesListFromNetwork = getShopsListFromNetwork()
            emit(handleShopsListFromNetwork(trufflesListFromNetwork))
        } else {
            emit(shopsListFromCache)
        }
    }

    private suspend fun getShopsListFromCache(): DataState<List<Shop>> = try {
        Timber.d("Trying to get Shops from the $CACHE...")
        val shops = shopDao.getAllShops()
        DataState(entityMapper.fromEntityList(shops))
    } catch (exception: Exception) {
        handleError(exception.message, CACHE)
    }

    private suspend fun getShopsListFromNetwork(): DataState<List<Shop>> = try {
        Timber.d("Trying to get Shops from the $NETWORK...")
        val response = shopService.getShopList()
        response.takeIf { it.isSuccessful }?.body()?.let {
            DataState(dtoMapper.toDomainList(it))
        } ?: handleError(response.message(), NETWORK)
    } catch (exception: Exception) {
        handleError(exception.message, NETWORK)
    }

    private suspend fun handleShopsListFromNetwork(dataFromNetwork: DataState<List<Shop>>): DataState<List<Shop>> =
        try {
            dataFromNetwork.data.takeIf { !it.isNullOrEmpty() }?.let {
                shopDao.insertShops(entityMapper.toEntityList(it))
                dataFromNetwork
            } ?: handleError(dataFromNetwork.error, NETWORK)
        } catch (exception: Exception) {
            handleError(exception.message, NETWORK)
        }

    private fun handleError(exceptionMessage: String?, source: String): DataState<List<Shop>> {
        Timber.d("$source retrieval failed.")
        return DataState.error(exceptionMessage ?: "Unknown Error")
    }

    companion object {
        const val NETWORK = "network"
        const val CACHE = "cache"
    }

}