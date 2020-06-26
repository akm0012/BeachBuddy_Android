package com.andrewkingmarshall.beachbuddy.network.requests

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class AddGameRequest(
    @SerializedName("name") val name: String
) : Serializable