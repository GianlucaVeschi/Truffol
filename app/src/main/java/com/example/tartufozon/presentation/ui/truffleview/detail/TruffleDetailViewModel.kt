package com.example.tartufozon.presentation.ui.truffleview.detail

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tartufozon.domain.model.Truffle
import com.example.tartufozon.interactors.GetTruffleUseCase
import com.example.tartufozon.presentation.ui.truffleview.repo.TruffleRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

const val STATE_KEY_TRUFFLE = "truffle.state.truffle.key"

@HiltViewModel
class TruffleDetailViewModel @Inject constructor(
    private val truffleRepositoryImpl: TruffleRepositoryImpl,
    private val state: SavedStateHandle,
    private val getTruffleUseCase: GetTruffleUseCase
) : ViewModel() {

    val truffle: MutableState<Truffle?> = mutableStateOf(null)
    val loading = mutableStateOf(false)

    init {
        // restore if process dies
        state.get<Int>(STATE_KEY_TRUFFLE)?.let { truffleId ->
            onTriggerEvent(TruffleDetailEvent.GetTruffleDetailEvent(truffleId))
        }
    }

    fun onTriggerEvent(detailEvent: TruffleDetailEvent) {
        try {
            when (detailEvent) {
                //UseCase #1
                is TruffleDetailEvent.GetTruffleDetailEvent -> {
                    getTruffleDetailUseCase(detailEvent.id)
                }
            }
        } catch (e: Exception) {
            Timber.e("launchJob: Exception: ${e}, ${e.cause}")
            e.printStackTrace()
        }
    }

    private fun getTruffleDetailUseCase(truffleId: Int) {
        loading.value = true

        getTruffleUseCase.run(truffleId).onEach { dataState ->
            loading.value = dataState.loading

            dataState.data?.let {
                Timber.d(it.toString())
                truffle.value = it
            }

            dataState.error?.let { error ->
                Timber.e("newSearch: ${error}")
                // TODO("Handle error")
            }
        }.launchIn(viewModelScope)


        Timber.d(truffle.toString())

        loading.value = false
    }

}