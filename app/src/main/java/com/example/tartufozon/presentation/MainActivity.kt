package com.example.tartufozon.presentation

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.setContent
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import com.example.tartufozon.presentation.ui.Screens
import com.example.tartufozon.presentation.ui.profileview.ProfileScreen
import com.example.tartufozon.presentation.ui.shopview.list.ShopListScreen
import com.example.tartufozon.presentation.ui.shopview.list.ShopListViewModel
import com.example.tartufozon.presentation.ui.truffleview.list.TruffleListScreen
import com.example.tartufozon.presentation.ui.truffleview.list.TruffleListViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    //Is it a good idea to have all ViewModels here?
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
            //val title = remember { mutableStateOf("TruffleListScreen") }
            BuildScaffold(navController = navController)
        }
    }

    @Composable
    fun BuildScaffold(navController: NavHostController) {
        // A surface container using the 'background' color from the theme
        Surface(color = MaterialTheme.colors.background) {
            Scaffold(
                bottomBar = {
                    BuildBottomBar(navController = navController)
                }
            ) {
                ScreensController(navController = navController)
            }
        }
    }

    @Composable
    fun BuildBottomBar(navController: NavHostController) {
        val bottomNavScreens = listOf(
            Screens.TruffleListScreen,
            Screens.ShopListScreen,
            Screens.ProfileScreen
        )
        BottomNavigation(
            backgroundColor = Color(139, 69, 19)
        ) {
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.arguments?.getString(KEY_ROUTE)

            bottomNavScreens.forEach {
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
        navController: NavHostController
        //topBarTitle: MutableState<String>
    ) {
        NavHost(navController = navController, startDestination = Screens.ShopListScreen.route) {

            composable(Screens.TruffleListScreen.route) {
                //topBarTitle.value = "Truffles Screen"
                TruffleListScreen(truffleListViewModel)
            }

            composable(Screens.ShopListScreen.route) {
                //topBarTitle.value = "Shops Screen"
                ShopListScreen(shopListViewModel)
            }

            composable(Screens.ProfileScreen.route) {
                //topBarTitle.value = "Profile Screen"
                ProfileScreen()
            }

        }
    }
}

