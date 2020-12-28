package com.example.tartufozon

import android.os.Bundle
import android.util.Log
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
import androidx.navigation.findNavController
import com.example.tartufozon.network.ServiceBuilder
import com.example.tartufozon.network.TartufoService
import com.example.tartufozon.network.models.TartufoApiResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TruffleListFragment : Fragment() {

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
                        Text(text = "Vedi Tartufo")
                    }
                    Spacer(modifier = Modifier.padding(10.dp))
                    Button(onClick = {
                        Log.d(TAG, "onCreateView 2: pressed")
                        val request = ServiceBuilder.buildService(TartufoService::class.java)
                        val call = request.getTartufi()

                        call.enqueue(object : Callback<TartufoApiResponse> {

                            override fun onResponse(
                                call: Call<TartufoApiResponse>,
                                response: Response<TartufoApiResponse>
                            ) {
                                Toast.makeText(requireContext(), "SUCCESS ${response.body()}", Toast.LENGTH_SHORT).show()
                                Log.d(TAG, "onResponse: succesful")
                            }

                            override fun onFailure(call: Call<TartufoApiResponse>, t: Throwable) {
                                Toast.makeText(requireContext(), "FAIL ${t.message}", Toast.LENGTH_SHORT).show()
                                Log.d(TAG, "onFailure: ")
                            }
                        })
                    }) {
                        Text("chiama tartufi")
                    }
                }
            }
        }
    }

    companion object {
        private const val TAG = "TruffleListFragment"
    }
}