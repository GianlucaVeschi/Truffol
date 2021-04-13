package com.example.tartufozon.presentation

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import com.example.tartufozon.presentation.ui.Screens
import com.example.tartufozon.presentation.ui.profileview.ProfileScreen
import com.example.tartufozon.presentation.ui.shopview.list.ShopListScreen
import com.example.tartufozon.presentation.ui.shopview.list.ShopListViewModel
import com.example.tartufozon.presentation.ui.truffleview.list.TruffleListScreen
import com.example.tartufozon.presentation.ui.truffleview.list.TruffleListViewModel
import dagger.hilt.android.AndroidEntryPoint
import androidx.hilt.navigation.HiltViewModelFactory
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tartufozon.BaseApplication
import com.example.tartufozon.presentation.util.CustomConnectivityManager
import com.example.tartufozon.presentation.util.TAG
import kotlinx.coroutines.*
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var connectivityManager: CustomConnectivityManager

    override fun onStart() {
        super.onStart()
        connectivityManager.registerConnectionObserver(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        connectivityManager.unregisterConnectionObserver(this)
    }

    @ExperimentalComposeUiApi
    @ExperimentalMaterialApi
    @ExperimentalCoroutinesApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController: NavHostController = rememberNavController()
            val isNetworkAvailable = connectivityManager.isNetworkAvailable.value
            Timber.d("onCreate: IS NETWORK AVAILABLE? $isNetworkAvailable")

            Surface(color = MaterialTheme.colors.background) {
                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = {
                                Text(text = "Trüffol Ecommerce", color = Color.Black)
                            },
                            actions = {
                                IconButton(onClick = { Timber.d("Favorite clicked") }) {
                                    Icon(Icons.Default.Favorite, contentDescription = "Favorite Icon")
                                }
                            },
                            backgroundColor = Color.White
                        )
                    },
                    bottomBar = {
                        BottomNavBar(navController = navController)
                    }
                ) {
                    // Apply the padding globally to the whole BottomNavScreensController
                    Box(modifier = Modifier.padding(it)) {
                        BottomNavScreensController(navController = navController)
                    }
                }
            }
        }
    }

    @Composable
    fun BottomNavBar(navController: NavHostController) {

        val bottomNavScreens = listOf(
            Screens.TruffleListScreen,
            Screens.ShopListScreen,
            Screens.ProfileScreen
        )
        BottomNavigation(
            backgroundColor = Color.White
        ) {

            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.arguments?.getString(KEY_ROUTE)

            bottomNavScreens.forEach {
                BottomNavigationItem(
                    icon = { Icon(it.icon, contentDescription = null) },
                    selected = currentRoute == it.route,
                    label = { Text(text = it.label) },
                    onClick = {
                        navController.popBackStack(
                            navController.graph.startDestination, false
                        )
                        if (currentRoute != it.route) {
                            navController.navigate(it.route)
                        }
                    })
            }
        }
    }

    @ExperimentalComposeUiApi
    @ExperimentalMaterialApi
    @ExperimentalCoroutinesApi
    @Composable
    fun BottomNavScreensController(
        navController: NavHostController,

        ) {
        NavHost(
            navController = navController,
            startDestination = Screens.ShopListScreen.route
        ) {

            composable(Screens.TruffleListScreen.route) {
                val viewModel: TruffleListViewModel by viewModels()
                TruffleListScreen(
                    viewModel,
                    isNetworkAvailable = connectivityManager.isNetworkAvailable.value,
                )
            }

            composable(Screens.ShopListScreen.route) {
                val factory = HiltViewModelFactory(LocalContext.current, it)
                val viewModel: ShopListViewModel = viewModel("ShopListViewModel", factory)
                ShopListScreen(
                    viewModel,
                    isNetworkAvailable = connectivityManager.isNetworkAvailable.value
                )
            }

            composable(Screens.ProfileScreen.route) {
                ProfileScreen()
            }
        }
    }
}