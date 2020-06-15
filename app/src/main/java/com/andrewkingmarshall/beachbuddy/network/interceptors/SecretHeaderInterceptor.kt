package com.andrewkingmarshall.beachbuddy.network.interceptors

import com.andrewkingmarshall.beachbuddy.AppSecretHeader
import okhttp3.Interceptor
import okhttp3.Response

class SecretHeaderInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .addHeader("AppToken", AppSecretHeader)
            .build()

        return chain.proceed(request)
    }

}