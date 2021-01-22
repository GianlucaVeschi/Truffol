package com.example.tartufozon.presentation.ui.truffleview.detail

sealed class TruffleEvent{

    data class GetTruffleEvent(
        val id: Int
    ): TruffleEvent()

    data class GetLocalTruffleEvent(
        val id: Int
    ): TruffleEvent()

}