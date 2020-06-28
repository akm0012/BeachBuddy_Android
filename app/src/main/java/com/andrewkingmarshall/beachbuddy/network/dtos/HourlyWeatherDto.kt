package com.andrewkingmarshall.beachbuddy.network.dtos

import com.google.gson.annotations.SerializedName

data class HourlyWeatherDto(

    var dt: Long,

    var temp: Double,

    @SerializedName("feels_like")
    var feelsLikeTemp: Double,

    var humidity: Int,

    var clouds: Int,

    @SerializedName("wind_speed")
    var windSpeed: Double,

    var weather: List<WeatherSummeryDto>?

)