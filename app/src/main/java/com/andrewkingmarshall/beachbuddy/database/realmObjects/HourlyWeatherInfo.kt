package com.andrewkingmarshall.beachbuddy.database.realmObjects

import com.andrewkingmarshall.beachbuddy.network.dtos.HourlyWeatherDto
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class HourlyWeatherInfo(

    /**
     * We only want to save how ever many we want to show. So this is the index of the RecyclerView.
     *
     * So if we want to show 6 hours, this would be an Int from 0 - 5. That way old data is naturally purged.
     */
    @PrimaryKey
    var id: Int = 0,

    var time: Long = 0,

    var temp: Double = 0.0,

    var feelsLike: Double = 0.0,

    var humidity: Int = 0,

    var clouds: Int = 0,

    var windSpeed: Double = 0.0,

    var mainDescription: String = "",

    var secondaryDescription: String = "",

    var iconTemplate: String = ""

) : RealmObject() {

    constructor(index: Int, hourDto: HourlyWeatherDto) : this() {

        id = index

        time = hourDto.dt
        temp = hourDto.temp
        feelsLike = hourDto.feelsLikeTemp
        humidity = hourDto.humidity
        clouds = hourDto.clouds
        windSpeed = hourDto.windSpeed
        mainDescription = hourDto.weather?.get(0)?.main ?: ""
        secondaryDescription = hourDto.weather?.get(0)?.description ?: ""
        iconTemplate = hourDto.weather?.get(0)?.icon ?: ""
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as HourlyWeatherInfo

        if (id != other.id) return false
        if (time != other.time) return false
        if (temp != other.temp) return false
        if (feelsLike != other.feelsLike) return false
        if (humidity != other.humidity) return false
        if (clouds != other.clouds) return false
        if (windSpeed != other.windSpeed) return false
        if (mainDescription != other.mainDescription) return false
        if (secondaryDescription != other.secondaryDescription) return false
        if (iconTemplate != other.iconTemplate) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + time.hashCode()
        result = 31 * result + temp.hashCode()
        result = 31 * result + feelsLike.hashCode()
        result = 31 * result + humidity
        result = 31 * result + clouds
        result = 31 * result + windSpeed.hashCode()
        result = 31 * result + mainDescription.hashCode()
        result = 31 * result + secondaryDescription.hashCode()
        result = 31 * result + iconTemplate.hashCode()
        return result
    }


}