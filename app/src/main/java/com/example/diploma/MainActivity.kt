package com.example.diploma

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.diploma.databinding.ActivityMainBinding
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.messaging.*

class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Fetching bottomNavView
        val navView: BottomNavigationView = binding.navView
        // Fetching NavController to pair with bottomNav
        val navHostFragment = supportFragmentManager
                .findFragmentById(R.id.nav_host_fragment_activity_main) as NavHostFragment
        navController = navHostFragment.navController
        navView.setupWithNavController(navController)
        // Setup the action\app bar
        setupActionBarWithNavController(navController)

        // Hides app bar and/or bottom nav on certain fragments
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                // Hide all navigation when on login screen
                R.id.navigation_login -> {
                    navView.isVisible = false
                    supportActionBar?.hide()
                }
                // Show navigation when on screen that are part of BottomNav
                in listOf(
                    R.id.navigation_home,
                    R.id.navigation_dashboard,
                    R.id.navigation_contacts
                ) -> {
                    navView.isVisible = true
                    supportActionBar?.show()
                    supportActionBar?.setDisplayHomeAsUpEnabled(false)
                }
                // Hide BottomNav if anything else
                else -> {
                    navView.isVisible = false
                    supportActionBar?.show()
                    supportActionBar?.setDisplayHomeAsUpEnabled(true)
                }
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    override fun onBackPressed() {
        when (navController.currentDestination?.id) {
            R.id.navigation_home -> finish()

            in listOf(
                R.id.navigation_dashboard,
                R.id.navigation_contacts
            ) -> navController.popBackStack(R.id.navigation_home, false)

            else -> super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.bottom_nav_menu, menu) // TODO change menu to Logout, ?
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        Log.i("Options menu", "seelected")
        return super.onOptionsItemSelected(item)
    }
}