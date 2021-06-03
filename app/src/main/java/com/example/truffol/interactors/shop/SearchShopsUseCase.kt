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
        try {
            emit(DataState.loading())

            //Improve readability, remove when cache - network - cache logic works
//            val shopsFromNetwork = getShopsFromNetwork()
//            val shopsEntities = entityMapper.toEntityList(shopsFromNetwork)
//            shopDao.insertShops(shopsEntities)
//            val shops = getShopsFromCache()
//            emit(DataState.success(shops))

            shopDao.insertShops(entityMapper.toEntityList(getShopsFromNetwork()))
            emit(DataState.success(getShopsFromCache()))

        } catch (e: Exception) {
            emit(DataState.error<List<Shop>>(e.message ?: "Unknown Error"))
        }
    }

    // WARNING: This will throw exception if there is no network connection
    private suspend fun getShopsFromNetwork(): List<Shop> {
        Timber.d("Get Shops from the network")
        val shopsFromNetwork = shopService.getShopList().body()!! //Might fail
        return dtoMapper.toDomainList(shopsFromNetwork)
    }

    private suspend fun getShopsFromCache(): List<Shop> {
        Timber.d("Get Shops from the cache")
        return entityMapper.fromEntityList(
            shopDao.getAllShops()
        )
    }
}