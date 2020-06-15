package com.andrewkingmarshall.beachbuddy.job

import com.andrewkingmarshall.beachbuddy.inject.AppComponent
import com.andrewkingmarshall.beachbuddy.network.service.ApiService
import com.birbit.android.jobqueue.Params
import com.birbit.android.jobqueue.RetryConstraint
import timber.log.Timber
import javax.inject.Inject

class GetNotCompletedRequestedItemsJob : BaseJob(Params(UI_HIGH).requireNetwork()) {

    @Inject
    lateinit var apiService: ApiService

    override fun inject(appComponent: AppComponent) {
        super.inject(appComponent)
        appComponent.inject(this)
    }

    override fun onAdded() {
        Timber.i("Added job...")
    }

    override fun onRun() {
        Timber.i("Running job...")

        var response = apiService.getNotCompletedRequestedItems()




    }

    override fun shouldReRunOnThrowable(throwable: Throwable, runCount: Int, maxRunCount: Int): RetryConstraint {
        Timber.w("Error while running job: ${throwable.localizedMessage}")

        if (checkFor400Error(throwable)) {
            return RetryConstraint.CANCEL
        }

        val retryConstraint = RetryConstraint.createExponentialBackoff(runCount, INITIAL_BACK_OFF_DELAY_MS)
        Timber.d("Going to retry: $retryConstraint")
        return retryConstraint
    }

    override fun onCancel(cancelReason: Int, throwable: Throwable?) {
        Timber.w("Job cancelled!")
    }
}