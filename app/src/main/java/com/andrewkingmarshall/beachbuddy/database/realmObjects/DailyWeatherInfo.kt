package com.andrewkingmarshall.beachbuddy.database.realmObjects

import com.andrewkingmarshall.beachbuddy.network.dtos.DailyWeatherDto
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class DailyWeatherInfo(

    /**
     * We only want to save how ever many we want to show. So this is the index of the RecyclerView.
     *
     * So if we want to show 6 hours, this would be an Int from 0 - 5. That way old data is naturally purged.
     */
    @PrimaryKey
    var id: Int = 0,

    var time: Long = 0,

    var feelsLikeDay: Double = 0.0,

    var humidity: Int = 0,

    var rainMilliMeters: Double = 0.0,

    var mainDescription: String = "",

    var secondaryDescription: String = "",

    var iconTemplate: String = ""

) : RealmObject() {

    constructor(index: Int, dayDto: DailyWeatherDto) : this() {

        id = index

        time = dayDto.dt
        feelsLikeDay = dayDto.feelsLike.day
        humidity = dayDto.humidity
        rainMilliMeters = dayDto.rain
        mainDescription = dayDto.weather?.get(0)?.main ?: ""
        secondaryDescription = dayDto.weather?.get(0)?.description ?: ""
        iconTemplate = dayDto.weather?.get(0)?.icon ?: ""
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as DailyWeatherInfo

        if (id != other.id) return false
        if (time != other.time) return false
        if (feelsLikeDay != other.feelsLikeDay) return false
        if (humidity != other.humidity) return false
        if (rainMilliMeters != other.rainMilliMeters) return false
        if (mainDescription != other.mainDescription) return false
        if (secondaryDescription != other.secondaryDescription) return false
        if (iconTemplate != other.iconTemplate) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + time.hashCode()
        result = 31 * result + feelsLikeDay.hashCode()
        result = 31 * result + humidity
        result = 31 * result + rainMilliMeters.hashCode()
        result = 31 * result + mainDescription.hashCode()
        result = 31 * result + secondaryDescription.hashCode()
        result = 31 * result + iconTemplate.hashCode()
        return result
    }
}