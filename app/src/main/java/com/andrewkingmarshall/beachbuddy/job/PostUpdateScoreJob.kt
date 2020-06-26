package com.andrewkingmarshall.beachbuddy.job

import com.andrewkingmarshall.beachbuddy.database.updateScore
import com.andrewkingmarshall.beachbuddy.eventbus.PostUpdateScoreEvent
import com.andrewkingmarshall.beachbuddy.inject.AppComponent
import com.andrewkingmarshall.beachbuddy.network.requests.UpdateScoreRequest
import com.andrewkingmarshall.beachbuddy.network.service.ApiService
import com.birbit.android.jobqueue.Params
import com.birbit.android.jobqueue.RetryConstraint
import org.greenrobot.eventbus.EventBus
import timber.log.Timber
import javax.inject.Inject

class PostUpdateScoreJob(
    private val scoreId: String,
    private val updateScoreRequest: UpdateScoreRequest
) :
    BaseJob(Params(UI_HIGH).requireNetwork().groupBy(scoreId)) {

    @Inject
    lateinit var apiService: ApiService

    override fun inject(appComponent: AppComponent) {
        super.inject(appComponent)
        appComponent.inject(this)
    }

    override fun onAdded() {
        Timber.i("Marking Item as completed.")
        updateScore(scoreId, updateScoreRequest.winCount)
    }

    override fun onRun() {
        Timber.i("Running job...")

        apiService.updateScore(scoreId, updateScoreRequest).body()

        EventBus.getDefault().post(PostUpdateScoreEvent(true))
    }

    override fun shouldReRunOnThrowable(
        throwable: Throwable,
        runCount: Int,
        maxRunCount: Int
    ): RetryConstraint {
        Timber.w("Error while running job: ${throwable.localizedMessage}")

        return RetryConstraint.CANCEL
    }

    override fun onCancel(cancelReason: Int, throwable: Throwable?) {
        Timber.w("Job cancelled!")
        updateScore(scoreId, updateScoreRequest.winCount - 1)
        EventBus.getDefault().post(PostUpdateScoreEvent(true, throwable))
    }
}