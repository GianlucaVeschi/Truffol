package com.example.tartufozon.presentation.ui.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.ScrollableRow
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.tartufozon.domain.model.Truffle
import com.example.tartufozon.presentation.components.CircularIndeterminateProgressBar
import com.example.tartufozon.presentation.components.SearchAppBar
import com.example.tartufozon.presentation.components.TruffleCard
import com.example.tartufozon.presentation.components.TruffleCategoryChip
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TruffleListFragment : Fragment() {

    private val truffleListViewModel: TruffleListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return ComposeView(requireContext()).apply {
            setContent {

                val trufflesList = truffleListViewModel.trufflesList.value
                val query: String = truffleListViewModel.query.value
                val selectedCategory = truffleListViewModel.selectedCategory.value
                val loading = truffleListViewModel.loading.value

                Column(modifier = Modifier.padding(10.dp)) {
                    val scrollState = rememberScrollState()
                    BuildSearchBar(query, selectedCategory, scrollState.value)
                    BuildRecyclerView(truffles = trufflesList,loading)
                }
            }
        }
    }

    @Composable
    fun BuildSearchBar(
        query: String,
        selectedCategory: TruffleCategory?,
        scrollState: Float
    ) {
        SearchAppBar(
            query = query,
            onQueryChanged = truffleListViewModel::onQueryChanged,
            onExecuteSearch = truffleListViewModel::getReversedTruffleList,
            categories = getAllTruffleCategories(),
            selectedCategory = selectedCategory,
            onSelectedCategoryChanged = truffleListViewModel::onSelectedCategoryChanged,
            scrollPosition = scrollState,
            onChangeScrollPosition = truffleListViewModel::onChangeCategoryScrollPosition,
        )
    }


    @Composable
    fun BuildChipsBar(selectedCategory: TruffleCategory?, scrollState: ScrollState) {
        ScrollableRow(
            modifier = Modifier.padding(start = 8.dp, bottom = 8.dp),
            scrollState = scrollState
        ) {
            // restore scroll position after rotation
            scrollState.scrollTo(truffleListViewModel.categoryScrollPosition)
            for (category in getAllTruffleCategories()) {
                TruffleCategoryChip(
                    category = category.value,
                    isSelected = selectedCategory == category,
                    onSelectedCategoryChanged = {
                        truffleListViewModel.onChangeCategoryScrollPosition(scrollState.value)
                        truffleListViewModel.onSelectedCategoryChanged(it)
                    },
                    onExecuteSearch = truffleListViewModel::getReversedTruffleList,
                )
            }
        }
    }

    @Composable
    fun BuildRecyclerView(truffles: List<Truffle>, isLoading : Boolean) {
        Box(modifier = Modifier.fillMaxSize()) {
            LazyColumn {
                itemsIndexed(
                    items = truffles
                ) { index, truffle ->

                    TruffleCard(truffle, onClick = {
                        Toast.makeText(
                            requireContext(),
                            "You just bought ${truffle.tartufoName} at $index!",
                            Toast.LENGTH_SHORT
                        ).show()
                    })
                }
            }
            CircularIndeterminateProgressBar(isDisplayed = isLoading)
        }
    }
}