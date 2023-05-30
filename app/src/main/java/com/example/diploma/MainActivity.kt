package com.example.diploma

import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.diploma.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

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
            in listOf(
                R.id.navigation_home
            )-> finish()

            in listOf(
                R.id.navigation_dashboard,
                R.id.navigation_contacts
            ) -> navController.popBackStack(R.id.navigation_home, false)

            else -> super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.options_menu, menu) // TODO change menu to Logout, ?
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId){
            R.id.options_menu_logout -> {
                val sp = getSharedPreferences(
                    getString(R.string.app_name),
                    Context.MODE_PRIVATE
                )
                sp.edit {
                    putString("login_token", null)
                }

                navController.navigate(R.id.navigation_login, null,
                    NavOptions.Builder()
                        .setPopUpTo(R.id.navigation_home, true)
                        .build()
                )
                true
            }

            R.id.options_menu_notification -> {
                true
            }

            R.id.options_menu_profile -> {
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }
}