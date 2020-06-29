package com.andrewkingmarshall.beachbuddy.network.dtos

import com.google.gson.annotations.SerializedName

data class CurrentUvDto(

    val uv: Double,

    @SerializedName("uv_time")
    val uvTime: String,

    @SerializedName("uv_max")
    val uvMax: Double,

    @SerializedName("uv_max_time")
    val uvMaxTime: String,

    @SerializedName("safe_exposure_time")
    val safeExposureTime: SafeExposureTimeDto
)

