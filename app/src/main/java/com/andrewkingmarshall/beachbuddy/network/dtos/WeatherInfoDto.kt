package com.andrewkingmarshall.beachbuddy.network.dtos

data class WeatherInfoDto(

    var lat: Double,

    var lon: Double,

    var current: CurrentWeatherDto

    )