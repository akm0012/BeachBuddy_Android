package com.andrewkingmarshall.beachbuddy.ui.views.viewmodels

import android.text.TextUtils
import androidx.annotation.DrawableRes
import com.andrewkingmarshall.beachbuddy.R
import com.andrewkingmarshall.beachbuddy.database.realmObjects.CurrentWeather
import com.andrewkingmarshall.beachbuddy.enums.BeachConditionItemType
import com.andrewkingmarshall.beachbuddy.enums.BeachConditionItemType.*
import com.andrewkingmarshall.beachbuddy.extensions.capitalizeWords
import org.joda.time.DateTime

class BeachConditionItemViewModel(
    var itemType: BeachConditionItemType,

    var weatherInfo: CurrentWeather
) {

    @DrawableRes
    fun getIconDrawable(): Int {
        return when (itemType) {
            CLOUD_COVERAGE_PERCENT -> R.drawable.ic_clouds_100
            WIND -> R.drawable.ic_air_100
            RESPIRATORY_IRRITATION -> R.drawable.ic_particle_100
            SURF -> R.drawable.ic_surfing_100
            JELLY_FISH -> R.drawable.ic_jellyfish_100
            TIME_UPDATED -> R.drawable.ic_delivery_time_100
        }
    }

    fun getTitle(): String {
        return when (itemType) {
            CLOUD_COVERAGE_PERCENT -> "Cloud Coverage"
            WIND -> "Wind"
            RESPIRATORY_IRRITATION -> "Respiratory Irritation"
            SURF -> "Surf"
            JELLY_FISH -> "Jelly Fish"
            TIME_UPDATED -> "Updated At"
        }
    }

    fun getContent(): String {
        return when (itemType) {

            CLOUD_COVERAGE_PERCENT -> {
                "${weatherInfo.cloudPercent}%"
            }

            WIND -> "${weatherInfo.windSpeed} mph ${weatherInfo.windDeg}°"

            RESPIRATORY_IRRITATION -> weatherInfo.beachConditions?.respiratoryIrritation?.capitalizeWords()
                ?: "N/A"

            SURF -> {

                val surfOverview = weatherInfo.beachConditions?.surf?.capitalizeWords()
                    ?: "N/A"

                var surfHeight = weatherInfo.beachConditions?.surfHeight?.capitalizeWords()
                    ?: ""

                if (!TextUtils.isEmpty(surfHeight)) {
                    surfHeight = "($surfHeight)"
                }

                "$surfOverview $surfHeight".trim()
            }

            JELLY_FISH -> weatherInfo.beachConditions?.jellyFish?.capitalizeWords()
                ?: "N/A"

            TIME_UPDATED -> {
                DateTime(weatherInfo.beachConditions?.timeUpdated).toString("EE h:mm a")
            }
        }
    }

}