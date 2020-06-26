package com.andrewkingmarshall.beachbuddy.network.service

import android.content.Context
import com.andrewkingmarshall.beachbuddy.BuildConfig
import com.andrewkingmarshall.beachbuddy.R
import com.andrewkingmarshall.beachbuddy.network.dtos.DashboardDto
import com.andrewkingmarshall.beachbuddy.network.dtos.RequestedItemDto
import com.andrewkingmarshall.beachbuddy.network.interceptors.ErrorInterceptor
import com.andrewkingmarshall.beachbuddy.network.interceptors.SecretHeaderInterceptor
import com.andrewkingmarshall.beachbuddy.network.requests.AddDeviceRequest
import com.andrewkingmarshall.beachbuddy.network.requests.AddGameRequest
import com.andrewkingmarshall.beachbuddy.network.requests.UpdateRequestedItemRequest
import com.andrewkingmarshall.beachbuddy.network.requests.UpdateScoreRequest
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Path

class ApiService(var context: Context) {

    private var apiServiceEndpointInterface: ApiServiceEndpointInterface

    init {
        val httpLoggingInterceptor = HttpLoggingInterceptor()

        // Set Log Level
        if (BuildConfig.DEBUG) {
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        } else {
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.NONE
        }

        // Set up the HTTP Client
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .addInterceptor(SecretHeaderInterceptor())
            .addInterceptor(ErrorInterceptor(context))
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(context.getString(R.string.base_endpoint))
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()

        apiServiceEndpointInterface = retrofit.create(ApiServiceEndpointInterface::class.java)
    }

    fun getNotCompletedRequestedItems(): Response<List<RequestedItemDto>> {
        val call = apiServiceEndpointInterface.getNonCompletedRequestedItems()

        return call.execute()
    }

    fun updateRequestedItem(
        requestedItemId: String,
        request: UpdateRequestedItemRequest
    ): Response<RequestedItemDto> {
        val call = apiServiceEndpointInterface.updateRequestedItem(requestedItemId, request)

        return call.execute()
    }

    fun addDevice(addDeviceRequest: AddDeviceRequest, callback: Callback<Void>) {
        val call = apiServiceEndpointInterface.addDevice(addDeviceRequest)

        call.enqueue(callback)
    }

    fun getDashboard(lat : Double, lon : Double): Response<DashboardDto> {
        val call = apiServiceEndpointInterface.getDashboard(lat, lon)

        return call.execute()
    }

    fun updateScore(
        scoreId: String,
        request: UpdateScoreRequest
    ): Response<Void> {
        val call = apiServiceEndpointInterface.updateScore(scoreId, request)

        return call.execute()
    }

    fun addGame(
        request: AddGameRequest,
        callback: Callback<Void>
    ) {
        val call = apiServiceEndpointInterface.addGame(request)

        call.enqueue(callback)
    }
}
