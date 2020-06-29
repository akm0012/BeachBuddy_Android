package com.andrewkingmarshall.beachbuddy.ui.views.viewmodels

import android.content.Context
import android.location.Address
import android.location.Geocoder
import androidx.annotation.ColorRes
import com.andrewkingmarshall.beachbuddy.R
import com.andrewkingmarshall.beachbuddy.database.realmObjects.CurrentWeather
import com.andrewkingmarshall.beachbuddy.enums.FlagColor.*
import com.andrewkingmarshall.beachbuddy.inject.Injector
import java.util.*
import javax.inject.Inject
import kotlin.math.roundToInt

class CurrentWeatherViewModel(private val currentWeather: CurrentWeather) {

    @Inject
    lateinit var context: Context

    init {
        Injector.obtain().inject(this)
    }

    fun getCityName(): String {
        val geocoder = Geocoder(context, Locale.getDefault())
        val addresses: List<Address> =
            geocoder.getFromLocation(currentWeather.latitude, currentWeather.longitude, 1)
        val locality = addresses[0].locality

        return locality
    }

    fun getWeatherDescription(): String {
        return currentWeather.mainDescription
    }

    fun getIconUrl(): String {
        return "http://openweathermap.org/img/wn/${currentWeather.iconTemplate}@2x.png"
    }

    fun getFeelsLikeTemp(): String {
        return "${currentWeather.feelsLikeTemp.roundToInt()}°"
    }

    @ColorRes
    fun getCardBackgroundColor(): Int {

        return when (currentWeather.beachConditions?.getFlagColorEnum()) {
            GREEN -> R.color.flag_green
            YELLOW -> R.color.flag_yellow
            PURPLE -> R.color.flag_purple
            RED -> R.color.flag_red
            DOUBLE_RED -> R.color.white
            UNKNOWN -> R.color.white
            null -> R.color.white
        }
    }

    @ColorRes
    fun getTextColor(): Int {
        return when (currentWeather.beachConditions?.getFlagColorEnum()) {
            GREEN -> R.color.white
            YELLOW -> R.color.dashboard_text_dark
            PURPLE -> R.color.white
            RED -> R.color.white
            DOUBLE_RED -> R.color.dashboard_text_dark
            UNKNOWN -> R.color.dashboard_text_dark
            null -> R.color.dashboard_text_dark
        }
    }

    @ColorRes
    fun getSecondaryTextColor(): Int {
        return when (currentWeather.beachConditions?.getFlagColorEnum()) {
            GREEN -> R.color.white
            YELLOW -> R.color.dashboard_text
            PURPLE -> R.color.white
            RED -> R.color.white
            DOUBLE_RED -> R.color.dashboard_text
            UNKNOWN -> R.color.dashboard_text
            null -> R.color.dashboard_text
        }
    }
}