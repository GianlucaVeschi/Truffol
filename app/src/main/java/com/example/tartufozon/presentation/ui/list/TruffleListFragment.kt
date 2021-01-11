package com.example.tartufozon.presentation.ui.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.tartufozon.BaseApplication
import com.example.tartufozon.domain.model.Truffle
import com.example.tartufozon.presentation.components.CircularIndeterminateProgressBar
import com.example.tartufozon.presentation.components.Fragmentz
import com.example.tartufozon.presentation.components.SearchAppBar
import com.example.tartufozon.presentation.components.TruffleCard
import com.example.tartufozon.presentation.components.shimmer.LoadingTruffleListShimmer
import com.example.tartufozon.presentation.components.theme.AppTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class TruffleListFragment : Fragment() {

    @Inject
    lateinit var application: BaseApplication

    private val truffleListViewModel: TruffleListViewModel by viewModels()
    val screens = listOf(
        Fragmentz.TruffleListFragment,
        Fragmentz.TruffleDetailFragment,
        Fragmentz.ProfileFragment
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return ComposeView(requireContext()).apply {
            setContent {
                AppTheme(
                    darkTheme = application.isDark.value
                ) {
                    val trufflesList = truffleListViewModel.trufflesList.value
                    val query: String = truffleListViewModel.query.value
                    val selectedCategory = truffleListViewModel.selectedCategory.value
                    val loading = truffleListViewModel.loading.value
                    val scrollState = rememberScrollState()

                    Scaffold(
                        topBar = { BuildSearchBar(query, selectedCategory, scrollState.value) },
                        bottomBar = { BuildBottomBar() },
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
            onExecuteSearch = truffleListViewModel::getShuffledTruffleList,
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
                            Toast.makeText(
                                requireContext(),
                                "You just bought ${truffle.tartufoName} at $index!",
                                Toast.LENGTH_SHORT
                            ).show()
                        })
                    }
                }
            }
            CircularIndeterminateProgressBar(isDisplayed = isLoading, verticalBias = 0.5f)
        }
    }

    @Composable
    fun BuildBottomBar() {
        BottomNavigation(
            elevation = 12.dp
        ) {
            screens.forEach {
                BottomNavigationItem(
                    icon = { Icon(it.icon) },
                    selected = false,
                    label = { Text(text = it.label) },
                    onClick = {})
            }
        }
    }

    @Composable
    fun BuildDrawerContent() {
        Column() {
            Text(text = "Item1")
            Text(text = "Item2")
            Text(text = "Item3")
            Text(text = "Item4")
            Text(text = "Item5")
        }
    }

}