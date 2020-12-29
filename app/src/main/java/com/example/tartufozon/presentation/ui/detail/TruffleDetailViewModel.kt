package com.example.tartufozon.presentation.ui.detail

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tartufozon.network.TruffleService
import kotlinx.coroutines.launch
import timber.log.Timber

class TruffleDetailViewModel @ViewModelInject constructor(
    private val service: TruffleService
) : ViewModel() {

    fun getTartufo() {
        viewModelScope.launch {
            val response = service.getTartufo()
            Timber.d("getTartufo: ${response.body()}")
        }
    }
}