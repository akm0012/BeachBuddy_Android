package com.andrewkingmarshall.beachbuddy.network.service

import com.andrewkingmarshall.beachbuddy.network.dtos.RequestedItemDto
import com.andrewkingmarshall.beachbuddy.network.requests.AddDeviceRequest
import com.andrewkingmarshall.beachbuddy.network.requests.UpdateRequestedItemRequest
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiServiceEndpointInterface {

    @GET("api/requestedItems/notCompleted")
    fun getNonCompletedRequestedItems(): Call<List<RequestedItemDto>>

    @POST("api/requestedItems/{requestedItemId}")
    fun updateRequestedItem(
        @Path("requestedItemId") requestedItemId: String,
        @Body request: UpdateRequestedItemRequest
    ): Call<RequestedItemDto>

    @POST("api/devices")
    fun addDevice(@Body addDeviceRequest: AddDeviceRequest): Call<Void>
}