package com.example.truffol.presentation.ui.truffleview.list

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.truffol.domain.model.Truffle
import com.example.truffol.presentation.components.shimmer.LoadingListShimmer
import com.example.truffol.presentation.ui.DetailScreens
import com.example.truffol.presentation.ui.Screens
import com.example.truffol.presentation.ui.truffleview.detail.TruffleDetailScreen
import com.example.truffol.presentation.ui.truffleview.detail.TruffleDetailViewModel
import androidx.hilt.navigation.HiltViewModelFactory
import androidx.navigation.NavType
import androidx.navigation.compose.navArgument
import com.example.truffol.presentation.components.*
import com.example.truffol.presentation.components.theme.AppTheme
import com.example.truffol.presentation.ui.ChipScreens
import com.example.truffol.presentation.ui.truffleview.chips.ChipBScreen
import com.example.truffol.presentation.ui.truffleview.chips.ChipCScreen
import com.example.truffol.presentation.ui.truffleview.chips.ChipDScreen
import com.example.truffol.presentation.ui.truffleview.chips.RecipesScreen
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalFoundationApi
@ExperimentalMaterialApi
@ExperimentalComposeUiApi
@ExperimentalCoroutinesApi
@Composable
fun TruffleListScreen(
    truffleListViewModel: TruffleListViewModel,
    truffleDetailViewModel: TruffleDetailViewModel,
    isNetworkAvailable: Boolean
) {
    //Each NavController must be associated with a single NavHost composable.
    val navHostController: NavHostController = rememberNavController()

    NavHost(navHostController, startDestination = Screens.TruffleListScreen.route) {

        composable(Screens.TruffleListScreen.route) {
            TruffleListScreenContent(truffleListViewModel, navHostController, isNetworkAvailable)
        }

        composable(
            route = DetailScreens.TruffleDetailScreen.route + "/{truffleId}",
            arguments = listOf(navArgument("truffleId") {
                type = NavType.IntType
            })
        ) {
            TruffleDetailScreen(
                truffleDetailViewModel = truffleDetailViewModel,
                truffleId = it.arguments?.getInt("truffleId")
            )
        }

        composable(ChipScreens.RecipesScreen.route) {
            RecipesScreen()
        }

        composable(ChipScreens.ChipBScreen.route) {
            ChipBScreen()
        }

        composable(ChipScreens.ChipCScreen.route) {
            ChipCScreen()
        }

        composable(ChipScreens.ChipDScreen.route) {
            ChipDScreen()
        }

    }
}

@ExperimentalFoundationApi
@ExperimentalCoroutinesApi
@ExperimentalMaterialApi
@ExperimentalComposeUiApi
@Composable
private fun TruffleListScreenContent(
    truffleListViewModel: TruffleListViewModel,
    navController: NavController,
    isNetworkAvailable: Boolean
) {
    val trufflesList = truffleListViewModel.trufflesList.value
    val query: String = truffleListViewModel.query.value
    val selectedCategory = truffleListViewModel.selectedCategory.value
    val loading = truffleListViewModel.loading.value
    val scrollState = rememberScrollState()
    val dialogQueue = truffleListViewModel.dialogQueue
    val scaffoldState = rememberScaffoldState()

    AppTheme(
        displayProgressBar = loading,
        darkTheme = false, //TODO(Add Toggle)
        scaffoldState = scaffoldState,
        dialogQueue = dialogQueue.queue.value,
        isNetworkAvailable = isNetworkAvailable
    ) {
        Scaffold(
            topBar = {
                //BuildSearchBar(truffleListViewModel, query, selectedCategory)
            },
        ) {
            Column() {
                TruffleCategoriesGrid(navController)
                BuildTrufflesList(truffles = trufflesList, loading, navController)
            }
        }
    }
}

@ExperimentalFoundationApi
@Composable
fun TruffleCategoriesGrid(navController: NavController) {
    val truffleBlocks = arrayOf(
        "RecipesScreen",
        "ChipBScreen",
        "ChipCScreen",
        "ChipDScreen"
    )
    TruffleCategoryBlock(truffleBlocks, navController)
}

@ExperimentalComposeUiApi
@Composable
fun BuildSearchBar(
    truffleListViewModel: TruffleListViewModel,
    query: String,
    selectedCategory: TruffleCategory?
) {
    // TODO: 22.04.21 : Complete this Api
    SearchAppBar(
        query = query,
        onQueryChanged = truffleListViewModel::onQueryChanged,
        onExecuteSearch = {
            truffleListViewModel.onTriggerEvent(TruffleListEvent.GetShuffledTruffleList)
        },
        categories = getAllTruffleCategories(),
        selectedCategory = selectedCategory,
        onSelectedCategoryChanged = truffleListViewModel::onSelectedCategoryChanged
    )
}

@ExperimentalCoroutinesApi
@Composable
fun BuildTrufflesList(truffles: List<Truffle>, isLoading: Boolean, navController: NavController) {
    Box(
        modifier = Modifier.background(color = MaterialTheme.colors.surface)
    ) {
        if (isLoading) {
            LoadingListShimmer(imageHeight = 250.dp)
        } else {
            LazyColumn {
                itemsIndexed(
                    items = truffles
                ) { index, truffle ->
                    TruffleCard(truffle, onClick = {
                        val route =
                            DetailScreens.TruffleDetailScreen.route + "/${truffle.truffleId}"
                        navController.navigate(route)
                    })
                }
            }
        }
    }
}