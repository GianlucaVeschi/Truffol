package com.example.tartufozon.presentation.ui.list

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tartufozon.network.TruffleService
import kotlinx.coroutines.launch
import timber.log.Timber

class TruffleListViewModel @ViewModelInject
constructor(
    private val service: TruffleService
) : ViewModel() {

    fun getTartufi() {
        viewModelScope.launch {
            val response = service.getTartufi()
            Timber.d("getTartufi: ${response.body()}")
        }
    }
}