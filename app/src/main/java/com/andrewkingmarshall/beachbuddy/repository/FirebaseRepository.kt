package com.andrewkingmarshall.beachbuddy.repository

import com.andrewkingmarshall.beachbuddy.inject.Injector
import com.andrewkingmarshall.beachbuddy.network.requests.AddDeviceRequest
import com.andrewkingmarshall.beachbuddy.network.service.ApiService
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.iid.FirebaseInstanceId
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber
import javax.inject.Inject

class FirebaseRepository {

    @Inject
    lateinit var apiService: ApiService

    init {
        Injector.obtain().inject(this)
    }

    fun registerDevice() {
        Timber.d("Registering Device...")

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

        Timber.d("Updating FCM Token: $fcmToken")

        apiService.addDevice(AddDeviceRequest(fcmToken), object : Callback<Void> {
            override fun onResponse(
                call: Call<Void?>,
                response: Response<Void?>
            ) {
                Timber.d("Fcm Token was sent up successfully.")
            }

            override fun onFailure(
                call: Call<Void?>,
                t: Throwable
            ) {
                Timber.w(t,"Something went wrong when updating fcm token: ${t.localizedMessage}")
            }
        })
    }
}

