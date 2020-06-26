package com.andrewkingmarshall.beachbuddy.repository

import com.andrewkingmarshall.beachbuddy.eventbus.PostUpdateScoreEvent
import com.andrewkingmarshall.beachbuddy.inject.Injector
import com.andrewkingmarshall.beachbuddy.job.PostUpdateScoreJob
import com.andrewkingmarshall.beachbuddy.network.requests.UpdateScoreRequest
import com.birbit.android.jobqueue.JobManager
import javax.inject.Inject

class ScoreRepository {

    @Inject
    lateinit var jobManager: JobManager

    init {
        Injector.obtain().inject(this)
    }

    fun updateScore(scoreId: String, winCount: Int) {
        jobManager.addJobInBackground(PostUpdateScoreJob(scoreId, UpdateScoreRequest(winCount)))
    }

}