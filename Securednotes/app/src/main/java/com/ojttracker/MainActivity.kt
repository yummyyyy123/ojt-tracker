package com.ojttracker

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView
import androidx.drawerlayout.widget.DrawerLayout
import com.ojttracker.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Set night mode based on system settings
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
        
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setupNavigation()
    }

    private fun setupNavigation() {
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        // Setup AppBarConfiguration
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_dashboard, R.id.nav_history, R.id.nav_settings
            ), binding?.drawerLayout
        )

        // Setup toolbar
        setSupportActionBar(binding?.toolbar)
        setupActionBarWithNavController(navController, appBarConfiguration)

        // Setup navigation drawer
        binding?.navView?.setupWithNavController(navController)

        // Setup bottom navigation
        binding?.bottomNavigation?.setupWithNavController(navController)

        // Handle navigation item clicks
        binding?.navView?.setNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_export -> {
                    // Handle export action
                    true
                }
                else -> {
                    // Let NavigationUI handle the rest
                    false
                }
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = (supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment).navController
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
