package com.example.tartufozon.presentation.ui.list

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tartufozon.presentation.ui.repo.TruffleRepositoryImpl
import kotlinx.coroutines.launch
import timber.log.Timber

class TruffleListViewModel @ViewModelInject
constructor(
    private val truffleRepositoryImpl: TruffleRepositoryImpl
) : ViewModel() {

    fun getTruffleList() {
        viewModelScope.launch {
            val trufflesList = truffleRepositoryImpl.getTruffleList()
            Timber.d("getTruffles from Repository: ${trufflesList}")
        }
    }
}