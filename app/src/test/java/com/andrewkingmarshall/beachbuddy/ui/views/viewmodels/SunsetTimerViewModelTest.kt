package com.andrewkingmarshall.beachbuddy.ui.views.viewmodels

import org.joda.time.DateTimeUtils
import org.junit.Test

import org.junit.Assert.*
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class SunsetTimerViewModelTest {

    // Sunset is 8:00pm and sunrise is 8:00am
    private val sunriseTime = 1593864000000L // 2020-07-04T08:00:00 EST
    private val sunsetTime = 1593907200000L  // 2020-07-04T20:00:00 EST
    private val sunriseNextDayTime = 1593950400000L // 2020-07-05T08:00:00 EST

    val viewModel = SunsetTimerViewModel(sunriseTime, sunsetTime, sunriseNextDayTime)

    @Test
    fun `getBottomLabel when midday`() {

        // It is midday before sunset
        val currentTime = 1593878400000L // 2020-07-04T12:00:00 EST

        val expeted = "SUNSET"

        val actual = viewModel.getBottomLabel(currentTime)

        assertEquals(expeted, actual)
    }

    @Test
    fun `getBottomLabel when after sunset and before midnight`() {

        // It is 11:00 pm
        val currentTime = 1593918000000L // 2020-07-04T23:00:00 EST

        val expeted = "SUNRISE"

        val actual = viewModel.getBottomLabel(currentTime)

        assertEquals(expeted, actual)
    }

    @Test
    fun `getBottomLabel when after sunset and after midnight`() {

        // It is 11:00 pm
        val currentTime = 1593925200000L // 2020-07-05T01:00:00 EST

        val expeted = "SUNRISE"

        val actual = viewModel.getBottomLabel(currentTime)

        assertEquals(expeted, actual)
    }

    @Test
    fun getTimerText() {
    }

    @Test
    fun getSubtitleTime() {
    }

    @Test
    fun getProgressInt() {
    }
}