package com.example.tartufozon.presentation.ui.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.example.tartufozon.R
import com.example.tartufozon.domain.model.Truffle
import com.example.tartufozon.presentation.components.TruffleCard
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
                for(truffle in trufflesList){
                    Timber.d( "onCreateView: ${truffle.image_url}" )
                }

                Column(modifier = Modifier.padding(10.dp)) {
                    Text(
                        text = "Hello from ListFragment",
                        style = TextStyle(
                            fontSize = TextUnit.Companion.Sp(21)),
                            textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.padding(10.dp))
                    Button(onClick = {
                        findNavController().navigate(R.id.action_truffleListFragment_to_truffleFragment)
                    }) {
                        Text(text = "Get Truffle")
                    }
                    Spacer(modifier = Modifier.padding(10.dp))
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