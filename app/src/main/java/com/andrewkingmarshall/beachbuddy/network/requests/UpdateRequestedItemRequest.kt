package com.andrewkingmarshall.beachbuddy.network.requests

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class UpdateRequestedItemRequest(
    @SerializedName("Name") val name: String,
    @SerializedName("Count") val count: Int,
    @SerializedName("isRequestCompleted") val isRequestCompleted: Boolean = true
): Serializable