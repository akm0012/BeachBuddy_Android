package com.andrewkingmarshall.beachbuddy.network.dtos

data class BeachConditionsDto(

    val id: Int,

    val name: String,

    val latest: LatestBeachConditionDto
)

