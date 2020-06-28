package com.andrewkingmarshall.beachbuddy.ui.views.viewmodels

import com.crashlytics.android.Crashlytics
import org.joda.time.DateTime
import org.joda.time.DateTimeZone
import org.joda.time.Period
import timber.log.Timber
import java.lang.IllegalStateException

class SunsetTimerViewModel(
    private val sunrise: Long,
    private val sunset: Long,
    private val sunriseNextDay: Long,
    private val sunsetPrevDay: Long
) {

    var hasErrorBeenReportedOnce = false

    fun getBottomLabel(currentTime: Long): String {
        return if (currentTime > sunset) {
            "SUNRISE"
        } else {
            "SUNSET"
        }
    }

    fun getTimerText(currentTime: Long): String {

        val currentDateTime = DateTime(currentTime).withZone(DateTimeZone.getDefault())

        return if (currentTime > sunset || currentTime < sunrise) {
            // We want to show the time until sunrise
            // Fixme: This is technically wrong as we are not using the sunrise time from the next day, but it's close enough
            val period = Period(currentDateTime, DateTime(sunriseNextDay))
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
            val sunriseDateTime = DateTime(sunriseNextDay)

            sunriseDateTime.withZone(DateTimeZone.getDefault()).toString("h:mm a")
        } else {
            // We want to show the sun set time
            val sunsetDateTime = DateTime(sunset)
            sunsetDateTime.withZone(DateTimeZone.getDefault()).toString("h:mm a")
        }
    }

    fun getProgressInt(currentTime: Long): Int {

        when {
            currentTime in sunrise until sunset -> {
                // Mid day before sunset
                return (((currentTime - sunrise).toDouble() / (sunset - sunrise)) * 100).toInt()
            }
            currentTime > sunrise && currentTime > sunset -> {
                // Night time before midnight
                return (((currentTime - sunset).toDouble() / (sunriseNextDay - sunset)) * 100).toInt()
            }
            currentTime < sunrise -> {
                // Night time after midnight, before sunrise
                return (((currentTime - sunsetPrevDay).toDouble() / (sunrise - sunsetPrevDay)) * 100).toInt()
            }
            else -> {
                val error =
                    IllegalStateException("We don't know what to do! " +
                            "[currentTime = $currentTime], " +
                            "[sunrise = $sunrise], " +
                            "[sunset = $sunset], " +
                            "[sunriseNextDay = $sunriseNextDay], " +
                            "[sunsetPrevDay = $sunsetPrevDay]")

                Timber.e(error)

                if (!hasErrorBeenReportedOnce) {
                    Crashlytics.logException(error)
                    hasErrorBeenReportedOnce = true
                }

                return 0
            }
        }
    }

}
