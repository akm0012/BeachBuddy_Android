package com.andrewkingmarshall.beachbuddy.network.dtos

import com.google.gson.annotations.SerializedName

data class CurrentWeatherDto(

    var dt: Long,

    var sunrise: Long,

    var sunset: Long,

    var temp: Double,

    @SerializedName("feels_like")
    var feelsLikeTemp: Double,

    var humidity: Int,

    var uvi: Double,

    var clouds: Int,

    @SerializedName("wind_speed")
    var windSpeed: Double,

    @SerializedName("wind_gust")
    var windGust: Double,

    @SerializedName("wind_deg")
    var windDeg: Int,

    var weather: List<WeatherSummeryDto>

)