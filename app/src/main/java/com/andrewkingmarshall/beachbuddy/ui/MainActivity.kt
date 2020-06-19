package com.andrewkingmarshall.beachbuddy.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.andrewkingmarshall.beachbuddy.R
import com.andrewkingmarshall.beachbuddy.viewmodels.MainActivityAndroidViewModel
import com.andrewkingmarshall.beachbuddy.viewmodels.RequestedItemAndroidViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var viewModel: MainActivityAndroidViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel =
            ViewModelProvider(this).get(MainActivityAndroidViewModel::class.java)

        setUpNavigation()
    }

    private fun setUpNavigation() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment

        val navController = navHostFragment.navController

        bottom_navigation_view.setupWithNavController(navController)

        viewModel.getNumberOfNotCompletedRequestedItems().observe(this, Observer {

            if (it == null || it == 0) {
                bottom_navigation_view.removeBadge(R.id.requestedItemsFragment)
            } else {
                bottom_navigation_view.getOrCreateBadge(R.id.requestedItemsFragment).number = it
            }
        })
    }
}
