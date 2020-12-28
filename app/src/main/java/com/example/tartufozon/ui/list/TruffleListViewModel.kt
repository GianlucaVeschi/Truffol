package com.example.tartufozon.ui.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tartufozon.network.ServiceBuilder
import com.example.tartufozon.network.TartufoService
import kotlinx.coroutines.launch
import timber.log.Timber

class TruffleListViewModel : ViewModel() {

    val service = ServiceBuilder.buildService(TartufoService::class.java)

    fun getTartufo() {
        viewModelScope.launch {
            val response = service.getTartufo()
            Timber.d( "getTartufo: ${response.body()}")
        }
    }
}