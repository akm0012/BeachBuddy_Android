package com.andrewkingmarshall.beachbuddy.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.andrewkingmarshall.beachbuddy.database.findAllRequestedItems
import com.andrewkingmarshall.beachbuddy.database.realmObjects.RequestedItem
import com.andrewkingmarshall.beachbuddy.inject.Injector
import com.andrewkingmarshall.beachbuddy.job.GetNotCompletedRequestedItemsJob
import com.birbit.android.jobqueue.JobManager
import io.realm.Realm
import javax.inject.Inject

class RequestedItemRepository {

    @Inject
    lateinit var jobManager: JobManager

    init {
        Injector.obtain().inject(this)
    }

    fun getAllRequestedItems(realm: Realm): LiveData<List<RequestedItem>> {

        refreshRequestedItems()

        return Transformations.map(findAllRequestedItems(realm)) {realm.copyFromRealm(it)}
    }

    fun refreshRequestedItems() {
        jobManager.addJobInBackground(GetNotCompletedRequestedItemsJob())
    }
}