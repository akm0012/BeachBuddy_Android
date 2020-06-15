package com.andrewkingmarshall.beachbuddy.job

import com.andrewkingmarshall.beachbuddy.exceptions.NetworkException
import com.andrewkingmarshall.beachbuddy.inject.AppComponent
import com.birbit.android.jobqueue.Job
import com.birbit.android.jobqueue.Params
import timber.log.Timber

const val INITIAL_BACK_OFF_DELAY_MS = 500L

const val UI_HIGH = 10
const val UI_MEDIUM = 5
const val BACKGROUND = 1

abstract class BaseJob(params: Params) : Job(params) {

    open fun inject(appComponent: AppComponent) {

    }

    open fun checkFor400Error(throwable: Throwable): Boolean {
        if (throwable is NetworkException) {
            if (throwable.httpErrorCode in 400..499) {
                Timber.w(throwable, "We got a 4xx error. cancelling.")
                return true
            }
        }
        return false
    }
}