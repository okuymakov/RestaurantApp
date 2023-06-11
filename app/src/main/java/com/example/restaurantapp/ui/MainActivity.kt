package com.example.restaurantapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.home.navigation.HomeNavigator
import com.example.home.ui.HomeFragmentDirections
import com.example.restaurantapp.R
import com.example.restaurantapp.di.AppComponent
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity(R.layout.activity_main), HomeNavigator {

    private val navController : NavController by  lazy {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.main_container) as NavHostFragment
        navHostFragment.navController
    }

    init {
        AppComponent.provideDeps(this)
    }

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        findViewById<BottomNavigationView>(R.id.bottom_navigation)
            .setupWithNavController(navController)
    }

    override fun navigateToCategory(categoryId: Long) {
        navController.navigate(HomeFragmentDirections.actionHomeFragmentToCategoryFragment(categoryId))
    }
}
