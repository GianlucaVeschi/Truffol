package com.example.truffol.interactors.truffle

import com.example.truffol.db.TruffleDao
import com.example.truffol.db.model.TruffleEntityMapper
import com.example.truffol.domain.model.Truffle
import com.example.truffol.domain.util.DataState
import com.example.truffol.network.TruffleService
import com.example.truffol.network.model.TruffleDtoMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/**
 * Retrieve a Truffle from the cache given it's unique id.
 */
class GetTruffleUseCase(
    private val truffleDao: TruffleDao,
    private val entityMapper: TruffleEntityMapper,
    private val truffleService: TruffleService,
    private val truffleDtoMapper: TruffleDtoMapper,
) {

    fun run(
        truffleId: Int,
    ): Flow<DataState<Truffle>> = flow {
        try {
            emit(DataState.loading())

            var truffle = getTruffleFromCache(truffleId = truffleId)

            if (truffle != null) {
                emit(DataState.success(truffle))
            }
            // if the Truffle is null, it means it was not in the cache for some reason. So get from network.
            else {

                // TODO("Check if there is an internet connection")
                // get Truffle from network
                val networkTruffle = getTruffleFromNetwork(truffleId) // dto -> domain

                // insert into cache
                truffleDao.insertTruffle(
                    // map domain -> entity
                    entityMapper.mapToDomainModel(networkTruffle)
                )

                // get from cache
                truffle = getTruffleFromCache(truffleId = truffleId)

                // emit and finish
                if (truffle != null) {
                    emit(DataState.success(truffle))
                } else {
                    throw Exception("Unable to get Truffle from the cache.")
                }
            }

        } catch (e: Exception) {
            emit(DataState.error<Truffle>(e.message ?: "Unknown Error"))
        }
    }

    private suspend fun getTruffleFromCache(truffleId: Int): Truffle? {
        return truffleDao.getTruffleById(truffleId)?.let { TruffleEntity ->
            entityMapper.mapFromDomainModel(TruffleEntity)
        }
    }

    private suspend fun getTruffleFromNetwork(truffleId: Int): Truffle {
        return truffleDtoMapper.mapToDomainModel(
            truffleService.getTruffleDetail(truffleId).body()!!
        )
    }
}