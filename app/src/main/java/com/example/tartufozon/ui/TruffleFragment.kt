package com.example.tartufozon.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.imageFromResource
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import com.example.tartufozon.R

class TruffleFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return ComposeView(requireContext()).apply {
            setContent {
                Column(modifier = Modifier.padding(10.dp)) {
                    Image(
                        bitmap = imageFromResource(
                            res = resources,
                            resId = R.drawable.tartufo_bianco
                        ),
                        modifier = Modifier.height(300.dp),
                        contentScale = ContentScale.Crop,
                    )
                    Text(
                        text = "Hello from $TAG",
                        style = TextStyle(fontSize = TextUnit.Sp(21))
                    )
                }
            }
        }
    }

    companion object {
        private const val TAG = "TruffleFragment"
    }
}