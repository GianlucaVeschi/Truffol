package com.example.tartufozon.presentation.ui.detail

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tartufozon.presentation.ui.repo.TruffleRepositoryImpl
import kotlinx.coroutines.launch
import timber.log.Timber

class TruffleDetailViewModel @ViewModelInject constructor(
    private val truffleRepositoryImpl: TruffleRepositoryImpl
) : ViewModel() {

    fun getTruffleDetail() {
        viewModelScope.launch {
            val truffle = truffleRepositoryImpl.getTruffleDetail()
            Timber.d("getTartufo from Repository: ${truffle}")
        }
    }
}