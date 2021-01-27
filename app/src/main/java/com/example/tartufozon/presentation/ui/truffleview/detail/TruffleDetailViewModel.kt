package com.example.tartufozon.presentation.ui.truffleview.detail

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tartufozon.domain.model.Truffle
import com.example.tartufozon.presentation.ui.truffleview.repo.TruffleRepositoryImpl
import kotlinx.coroutines.launch
import timber.log.Timber

const val STATE_KEY_TRUFFLE = "truffle.state.truffle.key"

class TruffleDetailViewModel @ViewModelInject constructor(
    private val truffleRepositoryImpl: TruffleRepositoryImpl,
    @Assisted private val state: SavedStateHandle,
) : ViewModel() {

    val truffle: MutableState<Truffle?> = mutableStateOf(null)
    val loading = mutableStateOf(false)

    init {
        // restore if process dies
        state.get<Int>(STATE_KEY_TRUFFLE)?.let{ truffleId ->
            onTriggerEvent(TruffleEvent.GetTruffleEvent(truffleId))
        }
    }

    fun onTriggerEvent(event: TruffleEvent){
        viewModelScope.launch {
            try {
                when(event){
                    //UseCase #1
                    is TruffleEvent.GetTruffleEvent -> {
                        //if(truffle.value == null){
                            getTruffleDetail(event.id)
                        //}
                    }

                }
            }catch (e: Exception){
                Timber.e("launchJob: Exception: ${e}, ${e.cause}")
                e.printStackTrace()
            }
        }
    }

    private suspend fun getTruffleDetail(truffleId : Int){
        loading.value = true

        val truffle = truffleRepositoryImpl.getTruffleDetail(truffleId)
        Timber.d("Gianluca $truffle")
        this.truffle.value = truffle

        state.set(STATE_KEY_TRUFFLE, truffle.id)

        loading.value = false
    }

}