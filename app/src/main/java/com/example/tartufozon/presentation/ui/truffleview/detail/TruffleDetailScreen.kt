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
import com.example.tartufozon.domain.model.Truffle
import com.example.tartufozon.presentation.components.TruffleDetailView


@Composable
fun TruffleDetailScreen(truffleName: String) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .then(Modifier.padding(8.dp)),
        horizontalAlignment = Alignment.End
    ) {
        //navController.previousBackStackEntry ?.arguments?.getParcelable<Truffle>("truffle_key")
        TruffleDetailScreenContent(truffleName = truffleName)
    }
}

@Composable
private fun TruffleDetailScreenContent(truffleName: String) {

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
            val truffle = Truffle(
                1,
                truffleName,
                "Buonisssimo",
                "https://www.moscatotartufi.it/wp-content/uploads/2015/03/vendita-tartufo-bianco-pregiato.jpg",
                9)
            TruffleDetailView(truffle = truffle)
        }
    }
}