package com.example.tartufozon.presentation.ui.truffleview.detail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.tartufozon.domain.model.Truffle
import com.example.tartufozon.presentation.components.ShopDetailView
import com.example.tartufozon.presentation.components.TruffleDetailView
import com.example.tartufozon.presentation.ui.shopview.detail.ShopDetailEvent
import com.example.tartufozon.util.Constants
import com.example.tartufozon.util.Constants.TRUFFLE_KEY
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