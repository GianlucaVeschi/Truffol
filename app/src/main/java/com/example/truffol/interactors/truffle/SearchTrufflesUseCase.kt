package com.example.truffol.interactors.truffle

import com.example.truffol.db.TruffleDao
import com.example.truffol.db.model.TruffleEntity
import com.example.truffol.db.model.TruffleEntityMapper
import com.example.truffol.domain.model.Truffle
import com.example.truffol.domain.util.DataState
import com.example.truffol.network.TruffleService
import com.example.truffol.network.model.TruffleDtoMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import timber.log.Timber

class SearchTrufflesUseCase(
    private val truffleDao: TruffleDao,
    private val truffleService: TruffleService,
    private val entityMapper: TruffleEntityMapper,
    private val dtoMapper: TruffleDtoMapper
) {

    fun run(): Flow<DataState<List<Truffle>>> = flow {
        emit(DataState.loading())

        //Try to get data from the cache
        val truffleListFromCache = getTrufflesListFromCache()

        //If Data is not in the cache then get it from the network and save it in the cache
        if (truffleListFromCache.data.isNullOrEmpty()) {
            val trufflesListFromNetwork: DataState<List<Truffle>> = getTrufflesListFromNetwork()
            trufflesListFromNetwork.data?.let {
                truffleDao.insertTruffles(entityMapper.toEntityList(it))
            }
        }

        //Finally emit data from the cache
        emit(getTrufflesListFromCache())
    }

    private suspend fun getTrufflesListFromCache(): DataState<List<Truffle>> = try {
        Timber.d("Get Truffles from the cache")
        val truffles = truffleDao.getAllTruffles()
        DataState(entityMapper.fromEntityList(truffles))
    } catch (exception: Exception) {
        DataState.error(exception.message ?: "Unknown Error")
    }

    // WARNING: This will throw exception if there is no network connection
    private suspend fun getTrufflesListFromNetwork(): DataState<List<Truffle>> = try {
        Timber.d("Get Truffles from the network")
        val response = truffleService.getTruffleList()
        response.takeIf { it.isSuccessful }?.body()?.let {
            DataState(dtoMapper.toDomainList(it))
        } ?: DataState.error(response.message() ?: "Unknown Error")
    } catch (exception: Exception) {
        DataState.error(exception.message ?: "Unknown Error")
    }

}