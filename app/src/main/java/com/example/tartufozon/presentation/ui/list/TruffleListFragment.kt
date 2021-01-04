package com.example.tartufozon.presentation.ui.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.ScrollableRow
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.tartufozon.domain.model.Truffle
import com.example.tartufozon.presentation.components.TruffleCard
import com.example.tartufozon.presentation.components.TruffleCategoryChip
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

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

                //Observe MutableState
                val trufflesList = truffleListViewModel.trufflesList.value
                val query: String = truffleListViewModel.query.value
                val selectedCategory = truffleListViewModel.selectedCategory.value

                Column(modifier = Modifier.padding(10.dp)) {
                    Surface(
                        modifier = Modifier.fillMaxWidth(),
                        color = Color.White,
                        elevation = 8.dp,
                    ) {
                        Column{
                            buildSearchBar(query = query)
                            Spacer(modifier = Modifier.padding(2.dp))
                            val scrollState = rememberScrollState()
                            buildChipsBar(selectedCategory,scrollState)
                        }
                    }
                    buildRecyclerView(truffles = trufflesList)
                }
            }
        }
    }

    @Composable
    fun buildSearchBar(query: String) {
        Row(modifier = Modifier.fillMaxWidth()) {
            TextField(
                modifier = Modifier
                    .fillMaxWidth(.9f)
                    .padding(8.dp),
                value = query,
                onValueChange = {
                    truffleListViewModel.onQueryChanged(it)
                },
                label = {
                    Text(text = "Search")
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Done,
                ),
                leadingIcon = {
                    Icon(Icons.Filled.Search)
                },
                onImeActionPerformed = { action, softKeyboardController ->
                    if (action == ImeAction.Done) {
                        truffleListViewModel.getReversedTruffleList() // TODO: 04.01.21 : should accept Query as parameter
                        softKeyboardController?.hideSoftwareKeyboard()
                    }
                },
                textStyle = TextStyle(color = MaterialTheme.colors.onSurface),
                backgroundColor = MaterialTheme.colors.surface
            )
        }
    }


@Composable
fun buildChipsBar(selectedCategory: TruffleCategory?, scrollState: ScrollState) {
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

fun testTimber(chipName: String) {
    Timber.d("You just clicked ${chipName} ")
}

@Composable
fun buildRecyclerView(truffles: List<Truffle>) {
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
}