package com.example.tartufozon.presentation.ui.truffleview.detail

import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun TruffleDetailScreenContent(){
    Column(
        modifier = Modifier.fillMaxSize().then(Modifier.padding(8.dp)),
        horizontalAlignment = Alignment.End
    ) {
        Text(text = "Truffle Detail Screen")
        Button(
            content = { Text("Useless button") },
            onClick = {}
        )
    }
}