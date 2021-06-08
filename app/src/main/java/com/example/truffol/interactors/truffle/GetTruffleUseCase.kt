package com.example.truffol.interactors.truffle

import com.example.truffol.db.TruffleDao
import com.example.truffol.db.model.TruffleEntityMapper
import com.example.truffol.domain.model.Truffle
import com.example.truffol.domain.util.DataState
import com.example.truffol.network.TruffleService
import com.example.truffol.network.model.TruffleDtoMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import timber.log.Timber

/**
 * Retrieve a Truffle from the cache given it's unique id.
 */
class GetTruffleUseCase(
    private val truffleDao: TruffleDao,
    private val entityMapper: TruffleEntityMapper,
    private val truffleService: TruffleService,
    private val dtoMapper: TruffleDtoMapper,
) {

    fun run(truffleId: Int): Flow<DataState<Truffle>> = flow {
        try {
            //Try to get data from the cache
            val truffleFromCache : DataState<Truffle> = getTruffleFromCache(truffleId)

            //If Data is not in the cache then get it from the network and save it in the cache
            if (truffleFromCache.data == null) {
                val truffleFromNetwork = getTruffleFromNetwork(truffleId)
                truffleFromNetwork.data?.let { insertTruffleIntoCache(it) }
                emit(truffleFromNetwork)
                //emit(handleTruffleFromNetwork(truffleFromNetwork))
            } else {
                emit(truffleFromCache)
            }

        } catch (e: Exception) {
            emit(DataState.error<Truffle>(e.message ?: "Unknown Error"))
        }
    }

    private suspend fun insertTruffleIntoCache(networkTruffle: Truffle) {
        truffleDao.insertTruffle(
            entityMapper.mapToDomainModel(networkTruffle)
        )
    }

    private suspend fun getTruffleFromCache2(truffleId: Int): Truffle? {
        return truffleDao.getTruffleById(truffleId)?.let { TruffleEntity ->
            entityMapper.mapFromDomainModel(TruffleEntity)
        }
    }

    private suspend fun getTruffleFromNetwork2(truffleId: Int): Truffle {
        // TODO("Check if there is an internet connection")
        return dtoMapper.mapToDomainModel(
            truffleService.getTruffleDetail(truffleId).body()!!
        )
    }

    private suspend fun getTruffleFromCache(truffleId: Int): DataState<Truffle> = try {
        Timber.d("Trying to get Truffle from the $CACHE...")
        val truffleEntity = truffleDao.getTruffleById(truffleId)
        val truffle = truffleEntity?.let { entityMapper.mapFromDomainModel(it) }
        DataState(truffle)
    } catch (exception: Exception) {
        handleError(exception.message, CACHE)
    }

    private suspend fun getTruffleFromNetwork(truffleId: Int): DataState<Truffle> = try {
        Timber.d("Trying to get Truffle from the $NETWORK...")
        val response = truffleService.getTruffleDetail(truffleId)
        response.takeIf { it.isSuccessful }?.body()?.let {
            DataState(dtoMapper.mapToDomainModel(it))
        } ?: handleError(response.message(), SearchTrufflesUseCase.NETWORK)
    } catch (exception: Exception) {
        handleError(exception.message, SearchTrufflesUseCase.NETWORK)
    }

    private fun handleError(exceptionMessage: String?, source: String): DataState<Truffle> {
        Timber.d("$source retrieval failed.")
        return DataState.error(exceptionMessage ?: "Unknown Error")
    }

    companion object {
        const val NETWORK = "network"
        const val CACHE = "cache"
    }
}