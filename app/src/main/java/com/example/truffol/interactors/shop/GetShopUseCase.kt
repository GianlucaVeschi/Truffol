package com.example.truffol.interactors.shop

import com.example.truffol.db.ShopDao
import com.example.truffol.db.model.ShopEntityMapper
import com.example.truffol.domain.model.Shop
import com.example.truffol.domain.util.DataState
import com.example.truffol.network.ShopService
import com.example.truffol.network.model.ShopDtoMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/**
 * Retrieve a shop from the cache given it's unique id.
 */
class GetShopUseCase(
    private val shopDao: ShopDao,
    private val entityMapper: ShopEntityMapper,
    private val shopService: ShopService,
    private val shopDtoMapper: ShopDtoMapper,
) {

    fun run(
        shopId: Int,
    ): Flow<DataState<Shop>> = flow {
        try {
            emit(DataState.loading())

            var shop = getShopFromCache(shopId = shopId)

            if (shop != null) {
                emit(DataState.success(shop))
            }
            // if the Shop is null, it means it was not in the cache for some reason. So get from network.
            else {

                // TODO("Check if there is an internet connection")
                // get Shop from network
                val networkShop = getShopFromNetwork(shopId) // dto -> domain

                // insert into cache
                shopDao.insertShop(
                    // map domain -> entity
                    entityMapper.mapToDomainModel(networkShop)
                )

                // get from cache
                shop = getShopFromCache(shopId = shopId)

                // emit and finish
                if (shop != null) {
                    emit(DataState.success(shop))
                } else {
                    throw Exception("Unable to get Shop from the cache.")
                }
            }

        } catch (e: Exception) {
            emit(DataState.error<Shop>(e.message ?: "Unknown Error"))
        }
    }

    private suspend fun getShopFromCache(shopId: Int): Shop? {
        return shopDao.getShopById(shopId)?.let { ShopEntity ->
            entityMapper.mapFromDomainModel(ShopEntity)
        }
    }

    private suspend fun getShopFromNetwork(shopId: Int): Shop {
        return shopDtoMapper.mapToDomainModel(
            shopService.getShopDetail(shopId).body()!!
        )
    }
}