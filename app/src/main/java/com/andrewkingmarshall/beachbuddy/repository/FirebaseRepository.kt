package com.andrewkingmarshall.beachbuddy.repository

import com.andrewkingmarshall.beachbuddy.inject.Injector
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.iid.FirebaseInstanceId
import timber.log.Timber

class FirebaseRepository {

    init {
        Injector.obtain().inject(this)
    }

    fun registerDevice() {
        FirebaseInstanceId.getInstance().instanceId
            .addOnCompleteListener(OnCompleteListener { task ->
                if (!task.isSuccessful) {
                    Timber.w(task.exception, "getInstanceId failed")
                    return@OnCompleteListener
                }

                // Get new Instance ID token
                val token = task.result?.token

                // Log and toast
                val msg = "InstanceID Token: $token"
                Timber.d(msg)

                token?.let { registerFCMToken(it) }
            })
    }

    fun registerFCMToken(fcmToken: String) {

        // First need to check if they are logged in.
//        if (accessTokenRepository.getAccessToken() == null) {
//            Timber.d("Access Token is null, not registering FCM Token")
//            return
//        }
//
//        authenticatedApiService.registerFCM(fcmToken, object : ApolloCall.Callback<RegisterFCMMutation.Data>() {
//            override fun onFailure(e: ApolloException) {
//                Timber.e(e, "Error while registering for FCM: ${e.localizedMessage}")
//            }
//
//            override fun onResponse(response: Response<RegisterFCMMutation.Data>) {
//                Timber.i("Registered for Push Notifications.")
//            }
//        })
    }
}

