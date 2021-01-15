package com.example.tartufozon.presentation.ui.list

sealed class TruffleListEvent {

    //Note that it would make more sense to use a class if we were passing smth to the event
    object GetTruffleListEvent : TruffleListEvent()

    object GetShuffledTruffleList : TruffleListEvent()
}