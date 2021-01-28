package com.example.tartufozon.presentation.ui.truffleview.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.tartufozon.BaseApplication
import com.example.tartufozon.R
import com.example.tartufozon.domain.model.Truffle
import com.example.tartufozon.presentation.components.BuildDrawerContent
import com.example.tartufozon.presentation.components.CircularIndeterminateProgressBar
import com.example.tartufozon.presentation.components.SearchAppBar
import com.example.tartufozon.presentation.components.TruffleCard
import com.example.tartufozon.presentation.components.shimmer.LoadingListShimmer
import com.example.tartufozon.presentation.components.theme.AppTheme
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class TruffleListFragment : Fragment() {

    @Inject
    lateinit var application: BaseApplication

    private val truffleListViewModel: TruffleListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {

                val trufflesList = truffleListViewModel.trufflesList.value
                val query: String = truffleListViewModel.query.value
                val selectedCategory = truffleListViewModel.selectedCategory.value
                val loading = truffleListViewModel.loading.value
                val scrollState = rememberScrollState()

                AppTheme(
                    darkTheme = application.isDark.value
                ) {
                    Scaffold(
                        topBar = { BuildSearchBar(query, selectedCategory, scrollState.value) },
                        drawerContent = { BuildDrawerContent() }
                    ) {
                        BuildTrufflesList(truffles = trufflesList, loading)
                    }
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
                application.toggleLightTheme()
            }
        )
    }

    @Composable
    fun BuildTrufflesList(truffles: List<Truffle>, isLoading: Boolean) {
        var click = 0
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
                            Timber.d("just clicked ${click++}")
                            val bundle = Bundle()
                            bundle.putInt("truffleId", truffle.id)
                            findNavController().navigate(R.id.action_truffleListFragment_to_truffleFragment, bundle)
                        })
                    }
                }
            }
            CircularIndeterminateProgressBar(isDisplayed = isLoading, verticalBias = 0.5f)
        }
    }
}