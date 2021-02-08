package com.example.tartufozon.presentation.ui.truffleview.detail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.tartufozon.domain.model.Truffle
import com.example.tartufozon.presentation.components.TruffleDetailView
import com.example.tartufozon.util.Constants.TRUFFLE_KEY


@Composable
fun TruffleDetailScreen(navController: NavController) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .then(Modifier.padding(8.dp)),
        horizontalAlignment = Alignment.End
    ) {
        val truffleDetail = navController.previousBackStackEntry?.arguments?.getParcelable<Truffle>(TRUFFLE_KEY)
        truffleDetail?.let {
            val scaffoldState = rememberScaffoldState()
            Scaffold(
                scaffoldState = scaffoldState,
                snackbarHost = {
                    scaffoldState.snackbarHostState
                }
            ) {
                Box(
                    modifier = Modifier.fillMaxSize()
                ) {
                    TruffleDetailView(truffle = truffleDetail)
                }
            }
        }
    }

}