package com.andrewkingmarshall.beachbuddy.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.andrewkingmarshall.beachbuddy.R
import com.andrewkingmarshall.beachbuddy.database.realmObjects.User
import com.andrewkingmarshall.beachbuddy.extensions.toast
import com.andrewkingmarshall.beachbuddy.ui.views.LeaderBoardView
import com.andrewkingmarshall.beachbuddy.ui.views.viewmodels.CurrentUvViewModel
import com.andrewkingmarshall.beachbuddy.viewmodels.DashboardAndroidViewModel
import com.badoo.mobile.util.WeakHandler
import kotlinx.android.synthetic.main.fragment_dashboard.*
import java.util.*

private const val AUTO_UPDATE_MILLIS = 5 * 60 * 1000L // 5 min

class DashboardFragment : Fragment() {

    lateinit var viewModel: DashboardAndroidViewModel

    lateinit var navController: NavController

    private var timer = Timer()
    private var handler: WeakHandler = WeakHandler()

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

        setUpSwipeToRefresh()

        setUpSunsetView()

        setUpCurrentWeatherAndBeachConditions()

        setUpHourlyWeatherView()

        setUpDailyWeatherView()

        setUpLeaderboard()

        viewModel.showToast.observe(viewLifecycleOwner, Observer { it.toast(requireContext()) })
    }

    private fun setUpSunsetView() {
        viewModel.getSunsetInfo().observe(viewLifecycleOwner, Observer {
            if (it == null) {
                return@Observer
            }

            sunsetTimerView.setSunsetSunriseTimes(
                it.sunrise,
                it.sunset,
                it.sunriseNextDay,
                it.sunsetPrevDay
            )
        })
    }

    private fun setUpCurrentWeatherAndBeachConditions() {
        viewModel.getCurrentWeather().observe(viewLifecycleOwner, Observer {
            if (it == null) {
                return@Observer
            }

            currentWeatherView.setWeather(it)
            beachConditionsView.setWeather(it)
            currentUvView.setViewModel(CurrentUvViewModel(it))
        })
    }

    private fun setUpHourlyWeatherView() {
        viewModel.getHourlyWeatherInfo().observe(viewLifecycleOwner, Observer {
            hourlyWeatherView.setWeather(it)
        })
    }

    private fun setUpDailyWeatherView() {
        viewModel.getDailyWeatherInfo().observe(viewLifecycleOwner, Observer {
            dailyWeatherView.setWeather(it)
        })
    }

    private fun setUpLeaderboard() {
        viewModel.getUsers().observe(viewLifecycleOwner, Observer {
            leaderBoardView.setUsers(it, object : LeaderBoardView.InteractionListener {
                override fun onSettingsClicked() {
                    navController.navigate(R.id.action_dashboardFragment_to_scoreManagementFragment)
                }

                override fun onUserClicked(user: User) {
                    currentUvView.showSafeExposureTimeForSkinType(user.skinType)
                }
            })
        })
    }

    override fun onResume() {
        super.onResume()

        startAutoUpdateTimer()
        sunsetTimerView.startTimer()
    }

    override fun onPause() {
        super.onPause()

        stopAutoUpdateTimer()
        sunsetTimerView.stopTimer()
    }

    private fun setUpSwipeToRefresh() {
        dashboardSwipeRefreshLayout.setColorSchemeColors(
            ContextCompat.getColor(requireContext(), R.color.colorPrimary),
            ContextCompat.getColor(requireContext(), R.color.colorAccent)
        )
        dashboardSwipeRefreshLayout.setOnRefreshListener { viewModel.onPullToRefresh() }
        viewModel.showLoadingEvent.observe(
            viewLifecycleOwner,
            Observer { dashboardSwipeRefreshLayout.isRefreshing = it })
    }


    private fun startAutoUpdateTimer() {
        timer = Timer()
        val myTimerTask = MyTimerTask()
        timer.schedule(myTimerTask, AUTO_UPDATE_MILLIS, AUTO_UPDATE_MILLIS)
    }

    private fun stopAutoUpdateTimer() {
        timer.cancel()
    }

    inner class MyTimerTask : TimerTask() {
        override fun run() {
            handler.post(MyRunnable())
        }
    }

    inner class MyRunnable : Runnable {
        override fun run() {
            viewModel.onAutoUpdatePeriodHit()
        }
    }
}
