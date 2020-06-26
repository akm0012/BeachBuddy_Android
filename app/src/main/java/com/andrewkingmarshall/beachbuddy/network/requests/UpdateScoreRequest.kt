package com.andrewkingmarshall.beachbuddy.network.requests

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class UpdateScoreRequest(
    @SerializedName("WinCount") val winCount: Int
) : Serializable