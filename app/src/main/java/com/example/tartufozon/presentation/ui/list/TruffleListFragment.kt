package com.example.tartufozon.presentation.ui.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.tartufozon.domain.model.Truffle
import com.example.tartufozon.presentation.components.TruffleCard
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

                //Observe MutableState
                val trufflesList = truffleListViewModel.trufflesList.value

                val query : String = truffleListViewModel.query.value

                Column(modifier = Modifier.padding(10.dp)) {
//                    Button(onClick = {
//                        findNavController().navigate(R.id.action_truffleListFragment_to_truffleFragment)
//                    }) {
//                        Text(text = "Get Truffle")
//                    }
//                    Spacer(modifier = Modifier.padding(10.dp))
                    buildSearchBar(query = query)
                    buildRecyclerView(truffles = trufflesList)
                }
            }
        }
    }

    @Composable
    fun buildSearchBar(query : String){
        Surface(
            modifier = Modifier.fillMaxWidth(),
            color = MaterialTheme.colors.primary,
            elevation = 8.dp,
        ){
            Row(modifier = Modifier.fillMaxWidth()){
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
    }

    @Composable
    fun buildRecyclerView(truffles: List<Truffle>){
        LazyColumn{
            itemsIndexed(
                items = truffles
            ){index, truffle ->
                TruffleCard(truffle, onClick = {
                    Toast.makeText(requireContext(), "You just bought ${truffle.tartufoName} at $index!", Toast.LENGTH_SHORT).show()
                })
            }
        }
    }
}