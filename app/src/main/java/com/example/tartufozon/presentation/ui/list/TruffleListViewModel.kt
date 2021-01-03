package com.example.tartufozon.presentation.ui.list

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tartufozon.domain.model.Truffle
import com.example.tartufozon.presentation.ui.repo.TruffleRepositoryImpl
import kotlinx.coroutines.launch
import timber.log.Timber

class TruffleListViewModel @ViewModelInject
constructor(
    private val truffleRepositoryImpl: TruffleRepositoryImpl
) : ViewModel() {

    val trufflesList: MutableState<List<Truffle>> = mutableStateOf(ArrayList())
    val query = mutableStateOf("White Truffle")

    init {
        getTruffleList()
    }

    private fun getTruffleList() {
        viewModelScope.launch {
            val tmpTrufflesList = truffleRepositoryImpl.getTruffleList()
            trufflesList.value = tmpTrufflesList.tartufi // TODO: 30.12.20 : Remove tmp list
            Timber.d("getTruffles ${trufflesList.value}")
        }
    }

    fun onQueryChanged(query: String){
        this.query.value = query
    }
}