package com.andrewkingmarshall.beachbuddy.database.realmObjects

import com.andrewkingmarshall.beachbuddy.network.dtos.BeachConditionsDto
import com.andrewkingmarshall.beachbuddy.network.dtos.WeatherInfoDto
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class CurrentWeather(

    @PrimaryKey
    var id: String = "CurrentWeatherPrimaryKey",

    var latitude: Double = 0.0,

    var longitude: Double = 0.0,

    var timeUpdated: Long = 0,

    var sunrise: Long = 0,

    var sunset: Long = 0,

    var temp: Double = 0.0,

    var feelsLikeTemp: Double = 0.0,

    var humidityPercent: Int = 0,

    var cloudPercent: Int = 0,

    // Unit: m/hr
    var windSpeed: Double = 0.0,

    var windGust: Double = 0.0,

    var windDeg: Int = 0,

    var mainDescription: String = "",

    var secondaryDescription: String = "",

    var iconTemplate: String = "",

    var beachConditions: BeachConditions? = null

) : RealmObject() {

    constructor(weatherDto: WeatherInfoDto, beachConditionsDto: BeachConditionsDto) : this() {

        val currentWeatherDto = weatherDto.current

        latitude = weatherDto.lat
        longitude = weatherDto.lon

        timeUpdated = currentWeatherDto.dt
        sunrise = currentWeatherDto.sunrise
        sunset = currentWeatherDto.sunset
        temp = currentWeatherDto.temp
        feelsLikeTemp = currentWeatherDto.feelsLikeTemp
        humidityPercent = currentWeatherDto.humidity
        cloudPercent = currentWeatherDto.clouds
        windSpeed = currentWeatherDto.windSpeed
        windGust = currentWeatherDto.windGust
        windDeg = currentWeatherDto.windDeg
        mainDescription = currentWeatherDto.weather[0].main
        secondaryDescription = currentWeatherDto.weather[0].description
        iconTemplate = currentWeatherDto.weather[0].icon

        beachConditions = BeachConditions(beachConditionsDto)
    }


    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as CurrentWeather

        if (id != other.id) return false
        if (latitude != other.latitude) return false
        if (longitude != other.longitude) return false
        if (timeUpdated != other.timeUpdated) return false
        if (sunrise != other.sunrise) return false
        if (sunset != other.sunset) return false
        if (temp != other.temp) return false
        if (feelsLikeTemp != other.feelsLikeTemp) return false
        if (humidityPercent != other.humidityPercent) return false
        if (cloudPercent != other.cloudPercent) return false
        if (windSpeed != other.windSpeed) return false
        if (windGust != other.windGust) return false
        if (mainDescription != other.mainDescription) return false
        if (secondaryDescription != other.secondaryDescription) return false
        if (iconTemplate != other.iconTemplate) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + latitude.hashCode()
        result = 31 * result + longitude.hashCode()
        result = 31 * result + timeUpdated.hashCode()
        result = 31 * result + sunrise.hashCode()
        result = 31 * result + sunset.hashCode()
        result = 31 * result + temp.hashCode()
        result = 31 * result + feelsLikeTemp.hashCode()
        result = 31 * result + humidityPercent
        result = 31 * result + cloudPercent
        result = 31 * result + windSpeed.hashCode()
        result = 31 * result + windGust.hashCode()
        result = 31 * result + mainDescription.hashCode()
        result = 31 * result + secondaryDescription.hashCode()
        result = 31 * result + iconTemplate.hashCode()
        return result
    }
}