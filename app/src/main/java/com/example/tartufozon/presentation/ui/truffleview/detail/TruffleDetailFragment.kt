package com.example.tartufozon.presentation.ui.truffleview.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.tartufozon.BaseApplication
import com.example.tartufozon.presentation.components.CircularIndeterminateProgressBar
import com.example.tartufozon.presentation.components.IMAGE_HEIGHT
import com.example.tartufozon.presentation.components.TruffleDetailView
import com.example.tartufozon.presentation.components.shimmer.LoadingDetailShimmer
import com.example.tartufozon.presentation.components.theme.AppTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

@AndroidEntryPoint
class TruffleDetailFragment : Fragment() {

    @Inject
    lateinit var application: BaseApplication

    private val truffleDetailViewModel: TruffleDetailViewModel by viewModels()

    private var truffleId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.getInt("truffleId")?.let { truffleId ->
            truffleDetailViewModel.onTriggerEvent(TruffleDetailEvent.GetTruffleDetailEvent(truffleId))
        }
    }

    @ExperimentalCoroutinesApi
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {

                val loading = truffleDetailViewModel.loading.value
                val truffle = truffleDetailViewModel.truffle.value
                val scaffoldState = rememberScaffoldState()

                AppTheme(
                    darkTheme = application.isDark.value,
                ) {
                    Scaffold(
                        scaffoldState = scaffoldState,
                        snackbarHost = {
                            scaffoldState.snackbarHostState
                        }
                    ) {
                        Box(
                            modifier = Modifier.fillMaxSize()
                        ) {
                            if (loading && truffle == null){
                               LoadingDetailShimmer(imageHeight = IMAGE_HEIGHT.dp)
                            }
                            else truffle?.let {
                                TruffleDetailView(truffle = it)
                            }
                            CircularIndeterminateProgressBar(
                                isDisplayed = loading,
                                verticalBias = 0.6f
                            )
                        }
                    }
                }
            }
        }
    }
}