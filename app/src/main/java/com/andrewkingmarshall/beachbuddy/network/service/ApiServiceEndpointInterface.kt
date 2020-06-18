package com.andrewkingmarshall.beachbuddy.network.service

import com.andrewkingmarshall.beachbuddy.network.dtos.RequestedItemDto
import retrofit2.Call
import retrofit2.http.GET

interface ApiServiceEndpointInterface {

    @GET("api/requestedItems/notCompleted")
    fun getNonCompletedRequestedItems(): Call<List<RequestedItemDto>>

}