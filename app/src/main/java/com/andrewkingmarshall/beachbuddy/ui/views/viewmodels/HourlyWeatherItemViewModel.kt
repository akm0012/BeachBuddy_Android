package com.andrewkingmarshall.beachbuddy.ui.views.viewmodels

import android.content.Context
import com.andrewkingmarshall.beachbuddy.database.realmObjects.HourlyWeatherInfo
import com.andrewkingmarshall.beachbuddy.extensions.capitalizeWords
import com.andrewkingmarshall.beachbuddy.inject.Injector
import org.joda.time.DateTime
import org.joda.time.DateTimeZone
import javax.inject.Inject
import kotlin.math.roundToInt

class HourlyWeatherItemViewModel(private val hourlyWeather: HourlyWeatherInfo) {

    @Inject
    lateinit var context: Context

    init {
        Injector.obtain().inject(this)
    }

    fun getTime(): String {
        return DateTime(hourlyWeather.time * 1000).withZone(DateTimeZone.getDefault())
            .toString("h:mm a")
    }

    fun getIconUrl(): String {
        return "http://openweathermap.org/img/wn/${hourlyWeather.iconTemplate}@2x.png"
    }

    fun getSummary(): String {
        return hourlyWeather.mainDescription.capitalizeWords()
    }

    fun getFeelsLike(): String {
        return "${hourlyWeather.feelsLike.roundToInt()}°"
    }

    fun getHumidity(): String {
        return "${hourlyWeather.humidity}%"
    }

}