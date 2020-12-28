package com.example.tartufozon.ui.repo

import com.example.tartufozon.network.models.TartufoApiResponse
import com.example.tartufozon.network.models.Truffle

interface TruffleRepository {
    suspend fun getTruffle(): Truffle

    suspend fun getTruffles(): TartufoApiResponse
}