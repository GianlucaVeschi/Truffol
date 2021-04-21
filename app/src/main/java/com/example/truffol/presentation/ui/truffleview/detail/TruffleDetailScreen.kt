package com.example.truffol.presentation.ui.truffleview.detail

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.truffol.presentation.components.TruffleDetailView
import com.example.truffol.util.Constants
import kotlinx.coroutines.ExperimentalCoroutinesApi


@ExperimentalCoroutinesApi
@Composable
fun TruffleDetailScreen(
    navController: NavController,
    truffleDetailViewModel: TruffleDetailViewModel
) {
    val truffleId = navController.previousBackStackEntry?.arguments?.getInt(Constants.TRUFFLE_KEY)!!
    truffleId.let {
        truffleDetailViewModel.onTriggerEvent(
            TruffleDetailEvent.GetTruffleDetailEvent(it)
        )

        truffleDetailViewModel.truffle.value?.let { truffleDetail ->
            TruffleDetailView(truffle = truffleDetail)
        }
    }

}