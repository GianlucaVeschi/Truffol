package com.example.tartufozon.presentation.ui.truffleview.detail

sealed class TruffleDetailEvent{

    data class GetTruffleDetailEvent(
        val id: Int
    ): TruffleDetailEvent()

}