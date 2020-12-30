package com.example.tartufozon.presentation.ui.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.example.tartufozon.R
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
                Column(modifier = Modifier.padding(10.dp)) {
                    Text(
                        text = "Hello from $TAG",
                        style = TextStyle(fontSize = TextUnit.Companion.Sp(21))
                    )
                    Spacer(modifier = Modifier.padding(10.dp))
                    Button(onClick = {
                        Toast.makeText(requireContext(), "NI HAO", Toast.LENGTH_SHORT).show()
                        findNavController().navigate(R.id.action_truffleListFragment_to_truffleFragment)
                    }) {
                        Text(text = "Get Truffle")
                    }
                    Spacer(modifier = Modifier.padding(10.dp))
                    Button(onClick = {
                        truffleListViewModel.getTruffleList()
                    }) {
                        Text("Get Truffles")
                    }
                }
            }
        }
    }

    companion object {
        private const val TAG = "TruffleListFragment"
    }
}