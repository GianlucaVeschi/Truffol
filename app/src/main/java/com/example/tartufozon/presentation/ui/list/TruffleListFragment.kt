package com.example.tartufozon.presentation.ui.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
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

                val query = truffleListViewModel.query.value

                Column(modifier = Modifier.padding(10.dp)) {
//                    Button(onClick = {
//                        findNavController().navigate(R.id.action_truffleListFragment_to_truffleFragment)
//                    }) {
//                        Text(text = "Get Truffle")
//                    }
//                    Spacer(modifier = Modifier.padding(10.dp))
                    TextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = query,
                        onValueChange = {
                            truffleListViewModel.onQueryChanged(it)
                        },
                        label = {
                            Text(text = "Search")
                        }
                    )
                    buildRecyclerView(truffles = trufflesList)
                }
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
                    Toast.makeText(requireContext(), "You just bought ${truffle.tartufoName}!", Toast.LENGTH_SHORT).show()
                })
            }
        }
    }
}