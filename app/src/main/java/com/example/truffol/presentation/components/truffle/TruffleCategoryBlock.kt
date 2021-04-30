package com.example.truffol.presentation.components

import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.SnackbarDefaults.backgroundColor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigate
import androidx.navigation.compose.rememberNavController
import com.example.truffol.presentation.ui.ChipScreens
import com.example.truffol.presentation.ui.Screens
import com.example.truffol.presentation.ui.profileview.ProfileScreenContent

@ExperimentalFoundationApi
@Composable
fun TruffleCategoryBlock(
    truffleBlocksNames: Array<String>,
    navController: NavController //Not used yet
) {
    val context = LocalContext.current
    LazyVerticalGrid(
        modifier = Modifier.padding(start = 10.dp),
        cells = GridCells.Fixed(2),
        contentPadding = PaddingValues(2.dp)
    ) {
        items(truffleBlocksNames) { blockName ->
            Button(
                modifier = Modifier.padding(all = 4.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color.Black,
                    contentColor = Color.White
                ),
                onClick = {
                    when (blockName) {
                        "ChipAScreen" -> navController.navigate(ChipScreens.ChipAScreen.route)
                        "ChipBScreen" -> navController.navigate(ChipScreens.ChipBScreen.route)
                        "ChipCScreen" -> navController.navigate(ChipScreens.ChipCScreen.route)
                        "ChipDScreen" -> navController.navigate(ChipScreens.ChipDScreen.route)
                        else -> Toast.makeText(
                            context, "Unknown destination",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            ) {
                Text(
                    text = blockName,
                    style = MaterialTheme.typography.body2,
                    color = Color.White,
                    modifier = Modifier.padding(4.dp)
                )
            }
        }
    }
}

@ExperimentalFoundationApi
@Preview
@Composable
fun PreviewTruffleCategoryBlock() {
    val truffleBlocks = arrayOf(
        "ChipAScreen",
        "ChipBScreen",
        "ChipCScreen",
        "ChipDScreen"
    )
    val navController: NavHostController = rememberNavController()
    TruffleCategoryBlock(truffleBlocks, navController)
}

