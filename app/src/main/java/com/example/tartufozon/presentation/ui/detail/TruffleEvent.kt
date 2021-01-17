package com.example.tartufozon.presentation.ui.detail

sealed class TruffleEvent{

    data class GetTruffleEvent(
        val id: Int
    ): TruffleEvent()

}