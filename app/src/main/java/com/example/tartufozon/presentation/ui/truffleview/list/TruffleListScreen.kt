package com.example.tartufozon.presentation.ui.truffleview.list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigate
import androidx.navigation.compose.rememberNavController
import com.example.tartufozon.domain.model.Truffle
import com.example.tartufozon.presentation.components.BuildDrawerContent
import com.example.tartufozon.presentation.components.CircularIndeterminateProgressBar
import com.example.tartufozon.presentation.components.SearchAppBar
import com.example.tartufozon.presentation.components.TruffleCard
import com.example.tartufozon.presentation.components.shimmer.LoadingListShimmer
import com.example.tartufozon.presentation.ui.DetailScreens
import com.example.tartufozon.presentation.ui.Screens
import com.example.tartufozon.presentation.ui.truffleview.detail.TruffleDetailScreen
import com.example.tartufozon.util.Constants.TRUFFLE_KEY
import kotlinx.coroutines.ExperimentalCoroutinesApi


@ExperimentalCoroutinesApi
@Composable
fun TruffleListScreen(
    truffleListViewModel: TruffleListViewModel
) {
    val navController: NavHostController = rememberNavController()

    NavHost(navController, startDestination = Screens.TruffleListScreen.route) {
        composable(Screens.TruffleListScreen.route) {
            TruffleListScreenContent(truffleListViewModel, navController)
        }
        composable(DetailScreens.TruffleDetailScreen.route) {
            TruffleDetailScreen(
                navController = navController
            )
        }
    }
}

@Composable
private fun TruffleListScreenContent(
    truffleListViewModel: TruffleListViewModel,
    navController: NavController
) {
    val trufflesList = truffleListViewModel.trufflesList.value
    val query: String = truffleListViewModel.query.value
    val selectedCategory = truffleListViewModel.selectedCategory.value
    val loading = truffleListViewModel.loading.value
    val scrollState = rememberScrollState()

    Scaffold(
        topBar = {
            BuildSearchBar(
                truffleListViewModel,
                query,
                selectedCategory,
                scrollState.value
            )
        },
        drawerContent = { BuildDrawerContent() }
    ) {
        BuildTrufflesList(truffles = trufflesList, loading, navController)
    }
}

@Composable
fun BuildSearchBar(
    truffleListViewModel: TruffleListViewModel,
    query: String,
    selectedCategory: TruffleCategory?,
    scrollState: Float
) {
    SearchAppBar(
        query = query,
        onQueryChanged = truffleListViewModel::onQueryChanged,
        onExecuteSearch = {
            truffleListViewModel
                .onTriggerEvent(TruffleListEvent.GetShuffledTruffleList)
        },
        categories = getAllTruffleCategories(),
        selectedCategory = selectedCategory,
        onSelectedCategoryChanged = truffleListViewModel::onSelectedCategoryChanged,
        scrollPosition = scrollState,
        onChangeScrollPosition = truffleListViewModel::onChangeCategoryScrollPosition,
        onToggleTheme = {
            //application.toggleLightTheme()
        }
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
                        navController.currentBackStackEntry?.arguments?.putParcelable(TRUFFLE_KEY, truffle)
                        navController.navigate(
                            DetailScreens.TruffleDetailScreen.route
                        )
                    })
                }
            }
        }
        CircularIndeterminateProgressBar(isDisplayed = isLoading, verticalBias = 0.5f)
    }
}