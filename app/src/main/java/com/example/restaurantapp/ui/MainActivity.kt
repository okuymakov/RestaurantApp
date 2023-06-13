package com.example.restaurantapp.ui

import android.Manifest
import android.location.Geocoder
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import coil.load
import com.example.category.navigation.CategoryNavigator
import com.example.category.ui.CategoryFragmentDirections
import com.example.domain.model.Dish
import com.example.home.navigation.HomeNavigator
import com.example.home.ui.HomeFragmentDirections
import com.example.restaurantapp.R
import com.example.restaurantapp.databinding.ActivityMainBinding
import com.example.restaurantapp.di.AppComponent
import com.example.restaurantapp.utils.location.LocationManager
import com.example.restaurantapp.utils.permissions.checkPermissions
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*


class MainActivity : AppCompatActivity(), HomeNavigator, CategoryNavigator {
    private lateinit var binding: ActivityMainBinding
    private val navHostFragment: NavHostFragment by lazy {
        supportFragmentManager.findFragmentById(R.id.main_container) as NavHostFragment
    }
    private val navController: NavController by lazy {
        navHostFragment.navController
    }

    private val requestLocationPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            if (permissions.values.any { it }) setupPlace()
            else binding.place.text = resources.getString(R.string.default_place)
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        AppComponent.provideDeps(this, this)
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        requestPermissions()
        binding.date.text = SimpleDateFormat("dd MMMM, yyyy", Locale.getDefault()).format(Date())
        binding.avatar.load("https://images.unsplash.com/photo-1567532939604-b6b5b0db2604?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=387&q=80")
        binding.bottomNavigation.setupWithNavController(navController)
        setupToolbar()
    }


    private fun requestPermissions() {
        val permissions = arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
        checkPermissions(permissions) { isGranted ->
            if (isGranted) {
                setupPlace()
            } else requestLocationPermissionLauncher.launch(permissions)
        }
    }

    private fun setupPlace() {
        val locationManager = LocationManager(
            applicationContext,
            LocationServices.getFusedLocationProviderClient(this)
        )
        lifecycleScope.launch {
            val location = locationManager.getCurrentLocation()
            binding.place.text = location?.let {
                val geocoder = Geocoder(this@MainActivity, Locale.getDefault())
                val addresses = geocoder.getFromLocation(it.latitude, it.longitude, 1)
                addresses?.get(0)?.locality
            } ?: resources.getString(R.string.default_place)
        }
    }

    private fun setupToolbar() {
        val toolbar = binding.toolbar
        toolbar.setNavigationOnClickListener {
            navController.popBackStack()
        }
        val config = arrayOf(R.id.home_fragment, R.id.cart_fragment)
        navController.addOnDestinationChangedListener { _, dest, _ ->
            if (!config.contains(dest.id)) {
                toolbar.navigationIcon =
                    ContextCompat.getDrawable(this@MainActivity, R.drawable.ic_arrow_back_24)
                binding.title.visibility = View.VISIBLE
                binding.place.visibility = View.GONE
                binding.date.visibility = View.GONE
                binding.locationIcon.visibility = View.GONE
            } else {
                binding.title.visibility = View.GONE
                binding.place.visibility = View.VISIBLE
                binding.date.visibility = View.VISIBLE
                binding.locationIcon.visibility = View.VISIBLE
                toolbar.navigationIcon = null
            }
        }
    }

    override fun navigateToCategory(category: String) {
        binding.title.text = category
        navController.navigate(
            HomeFragmentDirections.actionHomeFragmentToCategoryFragment(
                category
            )
        )
    }

    override fun navigateToProduct(dish: Dish) {
        navController.navigate(CategoryFragmentDirections.actionCategoryFragmentToProductDialog(dish))
    }
}
