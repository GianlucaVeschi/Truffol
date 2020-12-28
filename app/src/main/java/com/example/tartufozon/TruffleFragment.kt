package com.example.tartufozon

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.material.Text
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.TextUnit
import androidx.fragment.app.Fragment

class TruffleFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return ComposeView(requireContext()).apply {
            setContent {
                Text(
                    text = "Hello from ${TAG}",
                    style = TextStyle(fontSize = TextUnit.Sp(21))
                )
            }
        }
    }

    companion object{
        private const val TAG = "TruffleFragment"
    }
}