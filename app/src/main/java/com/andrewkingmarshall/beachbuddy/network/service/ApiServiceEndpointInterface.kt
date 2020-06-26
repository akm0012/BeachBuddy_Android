package com.andrewkingmarshall.beachbuddy.network.service

import com.andrewkingmarshall.beachbuddy.network.dtos.DashboardDto
import com.andrewkingmarshall.beachbuddy.network.dtos.RequestedItemDto
import com.andrewkingmarshall.beachbuddy.network.requests.AddDeviceRequest
import com.andrewkingmarshall.beachbuddy.network.requests.AddGameRequest
import com.andrewkingmarshall.beachbuddy.network.requests.UpdateRequestedItemRequest
import com.andrewkingmarshall.beachbuddy.network.requests.UpdateScoreRequest
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

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

    @GET("api/dashboard")
    fun getDashboard(@Query("lat") lat: Double, @Query("lon") lon: Double): Call<DashboardDto>

    @POST("api/updateScore/{scoreId}")
    fun updateScore(
        @Path("scoreId") scoreId: String,
        @Body updateScoreRequest: UpdateScoreRequest
    ): Call<Void>

    @POST("api/addScore")
    fun addGame(
        @Body addGameRequest: AddGameRequest
    ): Call<Void>
}