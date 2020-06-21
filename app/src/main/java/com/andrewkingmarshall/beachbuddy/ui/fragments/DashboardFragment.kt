package com.andrewkingmarshall.beachbuddy.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.andrewkingmarshall.beachbuddy.R
import kotlinx.android.synthetic.main.fragment_dashboard.*

class DashboardFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dashboard, container, false)
    }

    override fun onResume() {
        super.onResume()

        sunsetTimerView.setSunsetSunriseTimes(
            1592735774000,
            1592785675000,
            1592735774000
        )
        sunsetTimerView.startTimer()
    }

    override fun onPause() {
        super.onPause()

        sunsetTimerView.stopTimer()
    }

}
