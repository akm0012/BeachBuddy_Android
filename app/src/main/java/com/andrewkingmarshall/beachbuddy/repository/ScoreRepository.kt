package com.andrewkingmarshall.beachbuddy.repository

import com.andrewkingmarshall.beachbuddy.eventbus.PostUpdateScoreEvent
import com.andrewkingmarshall.beachbuddy.inject.Injector
import com.andrewkingmarshall.beachbuddy.job.PostUpdateScoreJob
import com.andrewkingmarshall.beachbuddy.network.NetworkCallStatusListener
import com.andrewkingmarshall.beachbuddy.network.requests.AddGameRequest
import com.andrewkingmarshall.beachbuddy.network.requests.UpdateScoreRequest
import com.andrewkingmarshall.beachbuddy.network.service.ApiService
import com.birbit.android.jobqueue.JobManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class ScoreRepository {

    @Inject
    lateinit var jobManager: JobManager

    @Inject
    lateinit var apiService: ApiService

    @Inject
    lateinit var dashboardRepository: DashboardRepository


    init {
        Injector.obtain().inject(this)
    }

    fun updateScore(scoreId: String, winCount: Int) {
        jobManager.addJobInBackground(PostUpdateScoreJob(scoreId, UpdateScoreRequest(winCount)))
    }

    fun addGame(gameName: String, listener: NetworkCallStatusListener<Void>?) {

        listener?.onStarted()

        apiService.addGame(AddGameRequest(gameName), object : Callback<Void> {

            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                response.body()?.let { listener?.onCompleted(it) }

                // Refresh the Dashboard / User Data
                dashboardRepository.refreshDashBoard()
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                listener?.onErrorOccurred(t)
            }
        })

    }

}