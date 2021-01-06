package com.example.tartufozon.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.tartufozon.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    lateinit var bottomNavigation: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
    }

    fun setBottomNav() {
        // Finding the Navigation Controller
        var navController = findNavController(R.id.main_nav_host_fragment)

        // Setting Navigation Controller with the BottomNavigationView
        bottomNavigation.setupWithNavController(navController)
    }
}

