package com.andrewkingmarshall.beachbuddy.network.dtos

import com.google.gson.annotations.SerializedName

data class DashboardDto(

    var users: List<UserDto>,

    var beachConditions: BeachConditionsDto,

    @SerializedName("weatherInfo")
    var weatherDto: WeatherInfoDto

)