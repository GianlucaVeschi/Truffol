package com.example.tartufozon.presentation.ui.truffleview.list

sealed class TruffleListEvent {

    //Note that it would make more sense to use a class if we were passing smth to the event
    object GetTruffleList : TruffleListEvent()

    object GetShuffledTruffleList : TruffleListEvent()
}