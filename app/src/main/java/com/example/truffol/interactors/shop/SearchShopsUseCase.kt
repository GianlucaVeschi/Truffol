package com.example.truffol.interactors.shop

import com.example.truffol.db.ShopDao
import com.example.truffol.db.model.ShopEntityMapper
import com.example.truffol.domain.model.Shop
import com.example.truffol.domain.util.DataState
import com.example.truffol.network.ShopService
import com.example.truffol.network.model.ShopDtoMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class SearchShopsUseCase(
    private val shopDao: ShopDao,
    private val shopService: ShopService,
    private val entityMapper: ShopEntityMapper,
    private val dtoMapper: ShopDtoMapper
) {

    fun run(): Flow<DataState<List<Shop>>> = flow {
        try {
            emit(DataState.loading())

            val shops = getShopsFromNetwork()

            // insert into cache
            shopDao.insertShops(entityMapper.toEntityList(shops))

            // TODO: 08.03.21 : query the cache
            val cacheResult = shopDao.getAllShops()

            val list = entityMapper.fromEntityList(cacheResult)

            emit(DataState.success(list))
        } catch (e: Exception) {
            emit(DataState.error<List<Shop>>(e.message ?: "Unknown Error"))
        }
    }

    // WARNING: This will throw exception if there is no network connection
    private suspend fun getShopsFromNetwork(): List<Shop> {
        return dtoMapper.toDomainList(
            shopService.getShopList().body()!!
        )
    }
}