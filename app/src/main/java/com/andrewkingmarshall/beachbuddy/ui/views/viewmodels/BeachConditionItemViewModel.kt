package com.andrewkingmarshall.beachbuddy.ui.views.viewmodels

import androidx.annotation.DrawableRes
import com.andrewkingmarshall.beachbuddy.R
import com.andrewkingmarshall.beachbuddy.database.realmObjects.CurrentWeather
import com.andrewkingmarshall.beachbuddy.enums.BeachConditionItemType

class BeachConditionItemViewModel(
    var itemType: BeachConditionItemType,

    var weatherInfo: CurrentWeather
) {

    @DrawableRes
    fun getIconDrawable() : Int {
        return R.drawable.ic_air_100
    }

    fun getTitle(): String {
        return "Wind"
    }

    fun getContent(): String {
        return "8 mph NW"
    }

}