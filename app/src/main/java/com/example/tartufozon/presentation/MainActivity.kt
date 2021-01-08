package com.example.tartufozon.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import com.example.tartufozon.R
import com.example.tartufozon.presentation.components.*
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Bottom Nav Works but still don't understand how to use use my fragments
//        setContent {
//            //BottomNav
//            val navController : NavHostController = rememberNavController()
//            val title = remember { mutableStateOf("Account") }
//            buildScaffold(navController = navController, title = title)
//        }
    }

    @Composable
    fun buildScaffold(navController : NavHostController, title : MutableState<String>){
        // A surface container using the 'background' color from the theme
        Surface(color = MaterialTheme.colors.background) {

            Scaffold(
                topBar = {
                    TopAppBar(title = { Text(text = title.value) },
                        actions = {
                            IconButton(onClick = {}) {
                                Icon(Icons.Default.Email)
                            }
                        })
                },

                bottomBar = {

                    val items = listOf(Screen.Account, Screen.DateRange, Screen.Edit, Screen.ThumbUp)
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
        NavHost(navController = navController, startDestination = "account") {
            composable("account") {
                AccountScreen()
                topBarTitle.value = "Account"
            }

            composable("date") {
                DateScreen()
                topBarTitle.value = "Date"
            }

            composable("edit") {
                EditScreen()
                topBarTitle.value = "Edit"
            }

            composable("like") {
                ThumpUpScreen()
                topBarTitle.value = "Like"
            }
        }
    }
}

