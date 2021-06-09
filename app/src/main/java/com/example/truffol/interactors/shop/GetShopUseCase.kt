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

/**
 * Retrieve a shop from the cache given it's unique id.
 */
class GetShopUseCase(
    private val shopDao: ShopDao,
    private val entityMapper: ShopEntityMapper,
    private val shopService: ShopService,
    private val dtoMapper: ShopDtoMapper,
) {

    fun run(shopId: Int): Flow<DataState<Shop>> = flow {
        try {
            //Try to get data from the cache
            val shopFromCache: DataState<Shop> = getShopFromCache(shopId)

            //If Data is not in the cache then get it from the network and save it in the cache
            if (shopFromCache.data == null) {
                val shopFromNetwork = getShopFromNetwork(shopId)
                shopFromNetwork.data?.let { insertShopIntoCache(it) }
                emit(shopFromNetwork)
                //emit(handleShopFromNetwork(shopFromNetwork)) //Might add later
            } else {
                emit(shopFromCache)
            }
        } catch (e: Exception) {
            emit(DataState.error<Shop>(e.message ?: "Unknown Error"))
        }
    }

    private suspend fun insertShopIntoCache(networkShop: Shop) {
        shopDao.insertShop(entityMapper.mapToDomainModel(networkShop))
    }

    private suspend fun getShopFromCache(shopId: Int): DataState<Shop> = try {
        Timber.d("Trying to get Shop from the $CACHE...")
        val shopEntity = shopDao.getShopById(shopId)
        val shop = shopEntity?.let { entityMapper.mapFromDomainModel(it) }
        DataState(shop)
    } catch (exception: Exception) {
        handleError(exception.message, CACHE)
    }

    private suspend fun getShopFromNetwork(shopId: Int): DataState<Shop> = try {
        Timber.d("Trying to get shop from the $NETWORK...")
        val response = shopService.getShopDetail(shopId)
        response.takeIf { it.isSuccessful }?.body()?.let {
            DataState(dtoMapper.mapToDomainModel(it))
        } ?: handleError(response.message(), NETWORK)
    } catch (exception: Exception) {
        handleError(exception.message, NETWORK)
    }

    private fun handleError(exceptionMessage: String?, source: String): DataState<Shop> {
        Timber.d("$source retrieval failed.")
        return DataState.error(exceptionMessage ?: "Unknown Error")
    }

    companion object {
        const val NETWORK = "network"
        const val CACHE = "cache"
    }
}