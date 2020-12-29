package com.example.tartufozon.presentation.ui.repo

import com.example.tartufozon.network.model.TartufoApiResponse
import com.example.tartufozon.domain.model.Truffle

interface TruffleRepository {
    suspend fun getTruffle(): Truffle

    suspend fun getTruffles(): TartufoApiResponse
}