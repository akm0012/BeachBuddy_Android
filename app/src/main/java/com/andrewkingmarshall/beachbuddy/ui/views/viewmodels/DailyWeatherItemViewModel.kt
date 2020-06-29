package com.andrewkingmarshall.beachbuddy.ui.views.viewmodels

import android.content.Context
import com.andrewkingmarshall.beachbuddy.database.realmObjects.DailyWeatherInfo
import com.andrewkingmarshall.beachbuddy.database.realmObjects.HourlyWeatherInfo
import com.andrewkingmarshall.beachbuddy.extensions.capitalizeWords
import com.andrewkingmarshall.beachbuddy.extensions.millimetersToInches
import com.andrewkingmarshall.beachbuddy.inject.Injector
import org.joda.time.DateTime
import org.joda.time.DateTimeZone
import javax.inject.Inject
import kotlin.math.roundToInt

class DailyWeatherItemViewModel(private val dailyWeather: DailyWeatherInfo) {

    @Inject
    lateinit var context: Context

    init {
        Injector.obtain().inject(this)
    }

    fun getTime(): String {
        return DateTime(dailyWeather.time * 1000).withZone(DateTimeZone.getDefault())
            .toString("EE")
    }

    fun getIconUrl(): String {
        return "http://openweathermap.org/img/wn/${dailyWeather.iconTemplate}@2x.png"
    }

    fun getSummary(): String {
        return dailyWeather.mainDescription.capitalizeWords()
    }

    fun getFeelsLike(): String {
        return "${dailyWeather.feelsLikeDay.roundToInt()}°"
    }

    fun getRain(): String {

        val millimetersToInches = dailyWeather.rainMilliMeters.millimetersToInches()

        val roundedInchesOfRain = String.format("%.2f", millimetersToInches).toDouble()

        return "$roundedInchesOfRain\""
    }

}