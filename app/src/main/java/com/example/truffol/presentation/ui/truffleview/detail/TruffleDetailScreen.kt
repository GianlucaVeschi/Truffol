package com.example.truffol.presentation.ui.truffleview.detail

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.truffol.presentation.components.TruffleDetailView
import com.example.truffol.util.Constants
import kotlinx.coroutines.ExperimentalCoroutinesApi


@ExperimentalCoroutinesApi
@Composable
fun TruffleDetailScreen(
    truffleDetailViewModel: TruffleDetailViewModel,
    truffleId: Int?
) {
    truffleId?.let {
        truffleDetailViewModel.onTriggerEvent(
            TruffleDetailEvent.GetTruffleDetailEvent(it)
        )

        truffleDetailViewModel.truffle.value?.let { truffleDetail ->
            TruffleDetailView(truffle = truffleDetail)
        }
    }
}