package com.example.tartufozon.presentation.ui.detail

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tartufozon.domain.model.Truffle
import com.example.tartufozon.presentation.ui.repo.TruffleRepositoryImpl
import kotlinx.coroutines.launch
import timber.log.Timber

class TruffleDetailViewModel @ViewModelInject constructor(
    private val truffleRepositoryImpl: TruffleRepositoryImpl
) : ViewModel() {

    //val truffleDetail: MutableState<Truffle> = remember { mutableStateOf(default) }
    private val _truffleDetail = MutableLiveData<Truffle>()
    val truffeDetail: LiveData<Truffle>
        get() = _truffleDetail

    init {
        Timber.d("viewmodel created")
        getTruffleDetail()
    }

    fun getTruffleDetail() {
        viewModelScope.launch {
            //_truffleDetail.postValue(truffleRepositoryImpl.getTruffleDetail())
            _truffleDetail.value = truffleRepositoryImpl.getTruffleDetail()
            Timber.d("getTartufo from Repository mutable: ${_truffleDetail.value}")
            Timber.d("getTartufo from Repository: ${truffeDetail.value?.image_url}")
        }
    }
}