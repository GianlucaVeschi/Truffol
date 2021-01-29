package com.example.tartufozon.presentation

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.setContent
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import com.example.tartufozon.presentation.components.Screens
import com.example.tartufozon.presentation.ui.profileview.ProfileScreen
import com.example.tartufozon.presentation.ui.shopview.list.ShopListScreen
import com.example.tartufozon.presentation.ui.shopview.list.ShopListViewModel
import com.example.tartufozon.presentation.ui.truffleview.list.TruffleListScreenContent
import com.example.tartufozon.presentation.ui.truffleview.list.TruffleListViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import timber.log.Timber


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    //Is it a good idea to have all viewmodels here?
    // They should live in they relative screens but it doesn't work...
    private val truffleListViewModel: TruffleListViewModel by viewModels()
    private val shopListViewModel: ShopListViewModel by viewModels()

    @ExperimentalCoroutinesApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Bottom Nav Works but still don't understand how it works exactly...
        setContent {
            //BottomNav
            val navController: NavHostController = rememberNavController()
            val title = remember { mutableStateOf("TruffleListScreen") }
            BuildScaffold(navController = navController, title = title)
        }
    }

    @Composable
    fun BuildScaffold(navController: NavHostController, title: MutableState<String>) {
        // A surface container using the 'background' color from the theme
        Surface(color = MaterialTheme.colors.background) {
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = { Text(text = title.value) },
                        actions = {
                            IconButton(onClick = {
                                Timber.d("Mail clicked")
                            }) {
                                Icon(Icons.Default.Email)
                            }
                        })
                },

                bottomBar = {
                    BuildBottomBar(navController = navController)
                }
            ) {
                ScreensController(navController = navController, topBarTitle = title)
            }
        }
    }

    @Composable
    fun BuildBottomBar(navController: NavHostController) {
        val items = listOf(
            Screens.TruffleListScreen,
            Screens.ShopListScreen,
            Screens.ProfileScreen
        )
        BottomNavigation(
            backgroundColor = Color(139, 69, 19)
        ) {
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.arguments?.getString(KEY_ROUTE)

            items.forEach {
                BottomNavigationItem(
                    icon = { Icon(it.icon) },
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

    @ExperimentalCoroutinesApi
    @Composable
    fun ScreensController(
        navController: NavHostController,
        topBarTitle: MutableState<String>
    ) {
        NavHost(navController = navController, startDestination = "gotoShopListScreen") {

            composable("gotoTruffleListScreen") {
                topBarTitle.value = "Truffles Screen"
                TruffleListScreenContent(truffleListViewModel, navController)
            }

            composable("gotoShopListScreen") {
                topBarTitle.value = "Shops Screen"
                ShopListScreen(shopListViewModel)
            }

            composable("gotoProfileScreen") {
                topBarTitle.value = "Profile Screen"
                ProfileScreen()
            }

        }
    }
}

