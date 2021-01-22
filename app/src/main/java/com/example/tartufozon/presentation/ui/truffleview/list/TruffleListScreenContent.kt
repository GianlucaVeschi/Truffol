package com.example.tartufozon.presentation.ui.truffleview.list

import android.os.Bundle
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.tartufozon.domain.model.Truffle
import com.example.tartufozon.presentation.components.BuildDrawerContent
import com.example.tartufozon.presentation.components.CircularIndeterminateProgressBar
import com.example.tartufozon.presentation.components.SearchAppBar
import com.example.tartufozon.presentation.components.TruffleCard
import com.example.tartufozon.presentation.components.shimmer.LoadingTruffleListShimmer
import kotlinx.coroutines.ExperimentalCoroutinesApi
import timber.log.Timber

@ExperimentalCoroutinesApi
@Composable
fun TruffleListScreenContent(truffleListViewModel: TruffleListViewModel) {
    //val truffleListViewModel: TruffleListViewModel = viewModel()

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
        BuildTrufflesList(truffles = trufflesList, loading)
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
fun BuildTrufflesList(truffles: List<Truffle>, isLoading: Boolean) {
    var click = 0
    Box(
        modifier = Modifier.background(color = MaterialTheme.colors.surface)
    ) {
        if (isLoading) {
            LoadingTruffleListShimmer(imageHeight = 250.dp)
        } else {
            LazyColumn {
                itemsIndexed(
                    items = truffles
                ) { index, truffle ->
                    TruffleCard(truffle, onClick = {
                        Timber.d("just clicked ${click++}")
                        val bundle = Bundle()
                        bundle.putInt("truffleId", truffle.id)
                        //findNavController().navigate(R.id.action_truffleListFragment_to_truffleFragment, bundle)
                    })
                }
            }
        }
        CircularIndeterminateProgressBar(isDisplayed = isLoading, verticalBias = 0.5f)
    }
}
