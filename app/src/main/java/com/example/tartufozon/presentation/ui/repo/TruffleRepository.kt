package com.example.tartufozon.presentation.ui.repo

import com.example.tartufozon.domain.model.Truffle
import com.example.tartufozon.network.model.TruffleDto

interface TruffleRepository {
    suspend fun getTruffleDetail(): Truffle

    suspend fun getTruffleList(): TruffleDto
}