package com.example.truffol.interactors.truffle

import com.example.truffol.db.TruffleDao
import com.example.truffol.db.model.TruffleEntityMapper
import com.example.truffol.domain.model.Truffle
import com.example.truffol.domain.util.DataState
import com.example.truffol.network.TruffleService
import com.example.truffol.network.model.TruffleDtoMapper
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

            truffleDao.insertTruffles(entityMapper.toEntityList(truffles))

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