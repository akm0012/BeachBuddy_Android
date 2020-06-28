package com.andrewkingmarshall.beachbuddy.network.dtos

import com.google.gson.annotations.SerializedName

data class LatestBeachConditionDto(

    val id: String = "BeachConditionsPrimaryKey",

    @SerializedName("updatedAt")
    val timeUpdated: String,

    val flag: String,

    val deadfish: String,

    val waterColor: String,

    val surf: String,

    val surfType: String,

    val surfHeight: String,

    val respiratoryIrritation: String,

    val waterTemp: String,

    val airTemp: String,

    val crowds: String,

    val jellyFish: String,

    val ripCurrent: String,

    val weatherSummary: String,

    val windSpeed: String,

    val redDrift: String

)