package com.andrewkingmarshall.beachbuddy.ui.views.viewmodels

import org.joda.time.DateTime
import org.joda.time.DateTimeZone
import org.joda.time.Period

class SunsetTimerViewModel(
    private val sunrise: Long,
    private val sunset: Long,
    private val sunriseNextDay: Long
) {

    fun getBottomLabel(currentTime: Long): String {
        return if (currentTime > sunset) {
            "SUNRISE"
        } else {
            "SUNSET"
        }
    }

    fun getTimerText(currentTime: Long): String {

        val currentDateTime = DateTime(currentTime)

        return if (currentTime > sunset || currentTime < sunrise) {
            // We want to show the time until sunrise
            // Fixme: This is technically wrong as we are not using the sunrise time from the next day, but it's close enough
            val sunriseDateTime = DateTime(sunrise).plusDays(1)
            val period = Period(currentDateTime, sunriseDateTime)
            "${period.hours}h ${period.minutes}m"
        } else {
            // We want to show the time until sunset
            val sunsetDateTime = DateTime(sunset)
            val period = Period(currentDateTime, sunsetDateTime)
            "${period.hours}h ${period.minutes}m"
        }
    }

    fun getSubtitleTime(currentTime: Long): String {
        return if (currentTime > sunset || currentTime < sunrise) {
            // We want to show the sun rise time
            // Fixme: This is technically wrong as we are not using the sunrise time from the next day, but it's close enough
            val sunriseDateTime = DateTime(sunrise).plusDays(1)

            sunriseDateTime.withZone(DateTimeZone.getDefault()).toString("h:mm a")
        } else {
            // We want to show the sun set time
            val sunsetDateTime = DateTime(sunset)
            sunsetDateTime.withZone(DateTimeZone.getDefault()).toString("h:mm a")
        }
    }

    fun getProgressInt(currentTime: Long): Int {
        // Fixme: This is technically wrong as we are not using the sunrise time from the next day, but it's close enough


        return if (currentTime > sunset || currentTime < sunrise) {
            // We want to show the progress until sunrise

            // sunset: 100
            // current time: 290
            // sunrise: 300

            // (current time - sunset) / (sunrise - sunset)

            // Millis in a day
//            val millisInDay = 24 * 60 * 60 * 1000
            val millisInDay = 0

            val midnight = DateTime().withZone(DateTimeZone.getDefault()).withTimeAtStartOfDay()

            val sunriseAdjusted = sunrise + millisInDay

            (((currentTime - sunset) / (sunriseAdjusted - sunset)) * 100).toInt()
        } else {
            // We want to show the progress until sunset

            // sunset: 400
            // current time: 290
            // sunrise: 200
            (((currentTime - sunrise) / (sunset - sunrise)) * 100).toInt()
        }
    }

}
