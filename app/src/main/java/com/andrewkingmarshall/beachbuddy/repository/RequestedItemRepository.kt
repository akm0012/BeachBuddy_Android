package com.andrewkingmarshall.beachbuddy.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.andrewkingmarshall.beachbuddy.database.findAllCompetedTodayRequestedItems
import com.andrewkingmarshall.beachbuddy.database.findAllRequestedNotCompletedItems
import com.andrewkingmarshall.beachbuddy.database.realmObjects.RequestedItem
import com.andrewkingmarshall.beachbuddy.inject.Injector
import com.andrewkingmarshall.beachbuddy.job.GetNotCompletedRequestedItemsJob
import com.andrewkingmarshall.beachbuddy.job.PostCompleteRequestedItemJob
import com.andrewkingmarshall.beachbuddy.network.requests.UpdateRequestedItemRequest
import com.birbit.android.jobqueue.JobManager
import io.realm.Realm
import javax.inject.Inject

class RequestedItemRepository {

    @Inject
    lateinit var jobManager: JobManager

    init {
        Injector.obtain().inject(this)
    }

    fun getNotCompletedRequestedItems(realm: Realm): LiveData<List<RequestedItem>> {

        refreshRequestedItems()

        return Transformations.map(findAllRequestedNotCompletedItems(realm)) { realm.copyFromRealm(it) }
    }

    fun getAllRequestedItemsThatWereCompletedToday(realm: Realm) : LiveData<List<RequestedItem>> {
        return Transformations.map(findAllCompetedTodayRequestedItems(realm)) { realm.copyFromRealm(it) }
    }

    fun refreshRequestedItems() {
        jobManager.addJobInBackground(GetNotCompletedRequestedItemsJob())
    }

    fun markRequestedItemAsComplete(requestedItem: RequestedItem) {
        jobManager.addJobInBackground(
            PostCompleteRequestedItemJob(
                requestedItem.id,
                UpdateRequestedItemRequest(requestedItem.name, requestedItem.count)
            )
        )
    }
}