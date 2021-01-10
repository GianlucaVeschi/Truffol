package com.example.tartufozon.presentation

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.runtime.*
import androidx.compose.ui.platform.setContent
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import com.example.tartufozon.R
import com.example.tartufozon.presentation.components.Profile
import com.example.tartufozon.presentation.components.Screen
import com.example.tartufozon.presentation.components.ShopsList
import com.example.tartufozon.presentation.components.TrufflesList
import com.example.tartufozon.presentation.ui.list.TruffleListViewModel
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val truffleListViewModel: TruffleListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Bottom Nav Works but still don't understand how to use use my fragments
        setContent {
            //BottomNav
            val navController : NavHostController = rememberNavController()
            val title = remember { mutableStateOf("Account") }
            buildScaffold(navController = navController, title = title)
        }
    }

    @Composable
    fun buildScaffold(navController : NavHostController, title : MutableState<String>){
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

                    val items = listOf(Screen.ShopsList, Screen.TrufflesList, Screen.Profile)
                    BottomNavigation {
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
            ) {
                ScreenController(navController, title)
            }
        }
    }

    @Composable
    fun ScreenController(navController: NavHostController, topBarTitle: MutableState<String>) {
        NavHost(navController = navController, startDestination = "shopslist") {
            composable("shopslist") {
                ShopsList()
                topBarTitle.value = "ShopsList"
            }

            composable("truffleslist") {
                TrufflesList()
                topBarTitle.value = "TrufflesList"
            }

            composable("profile") {
                Profile()
                topBarTitle.value = "Profile"
            }
        }
    }
}

