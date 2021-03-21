package com.example.tartufozon.interactors.truffle

import com.example.tartufozon.db.TruffleDao
import com.example.tartufozon.db.model.TruffleEntityMapper
import com.example.tartufozon.domain.model.Truffle
import com.example.tartufozon.domain.util.DataState
import com.example.tartufozon.network.TruffleService
import com.example.tartufozon.network.model.TruffleDtoMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class SearchTrufflesUseCase (
    private val truffleDao: TruffleDao,
    private val truffleService: TruffleService,
    private val entityMapper: TruffleEntityMapper,
    private val dtoMapper: TruffleDtoMapper
) {

    fun run(): Flow<DataState<List<Truffle>>> = flow {
        try {
            emit(DataState.loading())

            val truffles = getTrufflesFromNetwork()

            // insert into cache
            truffleDao.insertTruffles(entityMapper.toEntityList(truffles))

            // TODO: 08.03.21 : query the cache
            val cacheResult = truffleDao.getAllTruffles()

            val list = entityMapper.fromEntityList(cacheResult)

            emit(DataState.success(list))
        } catch (e: Exception) {
            emit(DataState.error<List<Truffle>>(e.message ?: "Unknown Error"))
        }
    }

    // WARNING: This will throw exception if there is no network connection
    private suspend fun getTrufflesFromNetwork(): List<Truffle> {
        return dtoMapper.toDomainList(
            truffleService.getTruffleList().body()!!
        )
    }
}