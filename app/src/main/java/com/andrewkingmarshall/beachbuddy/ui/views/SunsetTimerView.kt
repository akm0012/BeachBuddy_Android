package com.andrewkingmarshall.beachbuddy.ui.views

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import com.andrewkingmarshall.beachbuddy.R
import com.andrewkingmarshall.beachbuddy.ui.views.viewmodels.SunsetTimerViewModel
import com.badoo.mobile.util.WeakHandler
import kotlinx.android.synthetic.main.compound_view_sunset_timer.view.*
import java.util.*

private const val TIMER_DELAY = 1000L

class SunsetTimerView : FrameLayout {

    private var timer = Timer()
    private var handler: WeakHandler = WeakHandler()
    private var viewModel: SunsetTimerViewModel? = null

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    init {
        View.inflate(context, R.layout.compound_view_sunset_timer, this)
    }

    fun setSunsetSunriseTimes(
        sunrise: Long,
        sunset: Long,
        sunriseNextDay: Long
    ) {
        viewModel = SunsetTimerViewModel(sunrise, sunset, sunriseNextDay)
    }

    fun startTimer() {
        timer = Timer()
        val myTimerTask = MyTimerTask()
        timer.schedule(myTimerTask, TIMER_DELAY, TIMER_DELAY)
    }

    fun stopTimer() {
        timer.cancel()
    }

    private fun updateTimer() {
        val currentTime = System.currentTimeMillis()
        sunsetCountDownTextView.text = viewModel?.getTimerText(currentTime)
        sunsetTimeTextView.text = viewModel?.getSubtitleTime(currentTime)
        val progressInt = viewModel?.getProgressInt(currentTime) ?: 0
        arcProgressView.progress = progressInt
        arcProgressView.bottomText = viewModel?.getBottomLabel(currentTime)
    }

    inner class MyTimerTask : TimerTask() {
        override fun run() {
            handler.post(MyRunnable())
        }
    }

    inner class MyRunnable : Runnable {
        override fun run() {
            updateTimer()
        }
    }

}
