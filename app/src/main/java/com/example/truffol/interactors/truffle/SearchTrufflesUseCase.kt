package com.example.truffol.interactors.truffle

import android.net.Network
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
            emit(handleTruffleListFromNetwork(trufflesListFromNetwork))
        } else {
            emit(truffleListFromCache)
        }
    }

    private suspend fun getTrufflesListFromCache(): DataState<List<Truffle>> = try {
        Timber.d("Trying to get Truffles from the $CACHE...")
        val truffles = truffleDao.getAllTruffles()
        DataState(entityMapper.fromEntityList(truffles))
    } catch (exception: Exception) {
        handleError(exception.message, CACHE)
    }

    // WARNING: This will throw exception if there is no network connection
    private suspend fun getTrufflesListFromNetwork(): DataState<List<Truffle>> = try {
        Timber.d("Trying to get Truffles from the $NETWORK...")
        val response = truffleService.getTruffleList()
        response.takeIf { it.isSuccessful }?.body()?.let {
            DataState(dtoMapper.toDomainList(it))
        } ?: handleError(response.message(), NETWORK)
    } catch (exception: Exception) {
        handleError(exception.message , NETWORK)
    }

    private suspend fun handleTruffleListFromNetwork(dataFromNetwork: DataState<List<Truffle>>): DataState<List<Truffle>> =
        try {
            dataFromNetwork.data.takeIf { !it.isNullOrEmpty() }?.let {
                truffleDao.insertTruffles(entityMapper.toEntityList(it))
                dataFromNetwork
            } ?: handleError(dataFromNetwork.error, NETWORK)
        } catch (exception: Exception) {
            handleError(exception.message, NETWORK)
        }

    private fun handleError(exceptionMessage: String?, source: String): DataState<List<Truffle>> {
        Timber.d("$source retrieval failed.")
        return DataState.error(exceptionMessage ?: "Unknown Error")
    }

    companion object {
        const val NETWORK = "network"
        const val CACHE = "cache"
    }
}

