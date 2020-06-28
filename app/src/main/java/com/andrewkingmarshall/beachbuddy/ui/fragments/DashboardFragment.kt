package com.andrewkingmarshall.beachbuddy.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation

import com.andrewkingmarshall.beachbuddy.R
import com.andrewkingmarshall.beachbuddy.database.realmObjects.User
import com.andrewkingmarshall.beachbuddy.extensions.toast
import com.andrewkingmarshall.beachbuddy.ui.views.LeaderBoardView
import com.andrewkingmarshall.beachbuddy.viewmodels.DashboardAndroidViewModel
import kotlinx.android.synthetic.main.fragment_dashboard.*

class DashboardFragment : Fragment() {

    lateinit var viewModel: DashboardAndroidViewModel

    lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel =
            ViewModelProvider(requireActivity()).get(DashboardAndroidViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dashboard, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(view)

        setUpWeatherViews()

        setUpLeaderboard()

        viewModel.showToast.observe(viewLifecycleOwner, Observer { it.toast(requireContext()) })
    }

    private fun setUpWeatherViews() {
        viewModel.getCurrentWeather().observe(viewLifecycleOwner, Observer {
            if (it == null) {
                return@Observer
            }

            currentWeatherView.setWeather(it)
            beachConditionsView.setWeather(it)
        })
    }

    private fun setUpLeaderboard() {
        viewModel.getUsers().observe(viewLifecycleOwner, Observer {
            leaderBoardView.setUsers(it, object : LeaderBoardView.InteractionListener {
                override fun onSettingsClicked() {
                    navController.navigate(R.id.action_dashboardFragment_to_scoreManagementFragment)
                }

                override fun onUserClicked(user: User) {
                    navController.navigate(R.id.action_dashboardFragment_to_scoreManagementFragment)
                }
            })
        })
    }

    override fun onResume() {
        super.onResume()

        sunsetTimerView.setSunsetSunriseTimes(
            1592735774000,
            1592785675000,
            1592735774000,
            0
        )
        sunsetTimerView.startTimer()
    }

    override fun onPause() {
        super.onPause()

        sunsetTimerView.stopTimer()
    }

}
