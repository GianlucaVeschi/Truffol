package com.example.tartufozon.presentation.ui.truffleview.detail

sealed class TruffleEvent{

    data class GetTruffleEvent(
        val id: Int
    ): TruffleEvent()

}