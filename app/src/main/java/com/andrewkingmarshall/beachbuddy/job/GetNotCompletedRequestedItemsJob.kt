package com.andrewkingmarshall.beachbuddy.job

import com.andrewkingmarshall.beachbuddy.database.purgeDeletedRequestedItems
import com.andrewkingmarshall.beachbuddy.database.realmObjects.RequestedItem
import com.andrewkingmarshall.beachbuddy.eventbus.GetRequestedItemEvent
import com.andrewkingmarshall.beachbuddy.extensions.save
import com.andrewkingmarshall.beachbuddy.inject.AppComponent
import com.andrewkingmarshall.beachbuddy.network.service.ApiService
import com.birbit.android.jobqueue.Params
import com.birbit.android.jobqueue.RetryConstraint
import org.greenrobot.eventbus.EventBus
import timber.log.Timber
import java.lang.Exception
import javax.inject.Inject

class GetNotCompletedRequestedItemsJob : BaseJob(
    Params(UI_HIGH).requireNetwork()
        .singleInstanceBy(GetNotCompletedRequestedItemsJob::class.simpleName)
) {

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

        val itemDtos = apiService.getNotCompletedRequestedItems().body() ?: ArrayList()

        val itemsToSave = ArrayList<RequestedItem>()

        for (itemDto in itemDtos) {
            try {
                itemsToSave.add(RequestedItem(itemDto))
            } catch (e: Exception) {
                Timber.w(e, "Unable to process item. Skipping it. $itemDto")
                continue
            }
        }

        EventBus.getDefault().post(GetRequestedItemEvent(true))

        purgeDeletedRequestedItems(validRequestedItems = itemsToSave)
        itemsToSave.save()
    }

    override fun shouldReRunOnThrowable(
        throwable: Throwable,
        runCount: Int,
        maxRunCount: Int
    ): RetryConstraint {
        Timber.w("Error while running job: ${throwable.localizedMessage}")

        return RetryConstraint.CANCEL

//        if (checkFor400Error(throwable)) {
//            return RetryConstraint.CANCEL
//        }
//
//        val retryConstraint = RetryConstraint.createExponentialBackoff(runCount, INITIAL_BACK_OFF_DELAY_MS)
//        Timber.d("Going to retry: $retryConstraint")
//        return retryConstraint
    }

    override fun onCancel(cancelReason: Int, throwable: Throwable?) {
        Timber.w("Job cancelled!")
        EventBus.getDefault().post(GetRequestedItemEvent(true, throwable))
    }
}