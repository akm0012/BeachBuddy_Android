package com.andrewkingmarshall.beachbuddy.job

import com.andrewkingmarshall.beachbuddy.database.markRequestedItemAsNotCompleted
import com.andrewkingmarshall.beachbuddy.database.realmObjects.RequestedItem
import com.andrewkingmarshall.beachbuddy.eventbus.PostUpdateRequestedItemEvent
import com.andrewkingmarshall.beachbuddy.extensions.save
import com.andrewkingmarshall.beachbuddy.inject.AppComponent
import com.andrewkingmarshall.beachbuddy.network.requests.UpdateRequestedItemRequest
import com.andrewkingmarshall.beachbuddy.network.service.ApiService
import com.birbit.android.jobqueue.Params
import com.birbit.android.jobqueue.RetryConstraint
import org.greenrobot.eventbus.EventBus
import timber.log.Timber
import javax.inject.Inject

class PostCompleteRequestedItemJob(
    private val requestedItemId: String,
    private val updateRequestedItemRequest: UpdateRequestedItemRequest
) :
    BaseJob(Params(UI_HIGH).requireNetwork()) {

    @Inject
    lateinit var apiService: ApiService

    override fun inject(appComponent: AppComponent) {
        super.inject(appComponent)
        appComponent.inject(this)
    }

    override fun onAdded() {
        Timber.i("Marking Item as completed.")
//        markRequestedItemAsComplete(requestedItemId)
    }

    override fun onRun() {
        Timber.i("Running job...")

        val itemDto = apiService.updateRequestedItem(requestedItemId, updateRequestedItemRequest).body()

        if (itemDto != null) {
            try {
                val itemToSave = RequestedItem(itemDto)
                itemToSave.save()
            } catch (e: Exception) {
                Timber.w(e, "Unable to process item. Skipping it. $itemDto")
            }
        }

        EventBus.getDefault().post(PostUpdateRequestedItemEvent(true))
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
        markRequestedItemAsNotCompleted(requestedItemId)
        EventBus.getDefault().post(PostUpdateRequestedItemEvent(true, throwable))
    }
}