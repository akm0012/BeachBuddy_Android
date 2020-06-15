package com.andrewkingmarshall.beachbuddy.network.service

import android.content.Context
import com.andrewkingmarshall.beachbuddy.BuildConfig
import com.andrewkingmarshall.beachbuddy.R
import com.andrewkingmarshall.beachbuddy.network.dtos.RequestedItemDto
import com.andrewkingmarshall.beachbuddy.network.interceptors.ErrorInterceptor
import com.andrewkingmarshall.beachbuddy.network.interceptors.SecretHeaderInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

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

    fun getNotCompletedRequestedItems() : Response<List<RequestedItemDto>> {
        val call = apiServiceEndpointInterface.getNonCompletedRequestedItems()

        return call.execute()
    }

}
