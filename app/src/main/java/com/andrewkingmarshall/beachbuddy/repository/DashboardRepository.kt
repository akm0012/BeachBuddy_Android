package com.andrewkingmarshall.beachbuddy.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.andrewkingmarshall.beachbuddy.database.findAllCompetedTodayRequestedItems
import com.andrewkingmarshall.beachbuddy.database.findAllRequestedNotCompletedItems
import com.andrewkingmarshall.beachbuddy.database.findAllUsersForLeaderBoard
import com.andrewkingmarshall.beachbuddy.database.realmObjects.RequestedItem
import com.andrewkingmarshall.beachbuddy.database.realmObjects.User
import com.andrewkingmarshall.beachbuddy.inject.Injector
import com.andrewkingmarshall.beachbuddy.job.GetDashboardJob
import com.andrewkingmarshall.beachbuddy.job.GetNotCompletedRequestedItemsJob
import com.andrewkingmarshall.beachbuddy.job.PostCompleteRequestedItemJob
import com.andrewkingmarshall.beachbuddy.network.requests.UpdateRequestedItemRequest
import com.birbit.android.jobqueue.JobManager
import io.realm.Realm
import javax.inject.Inject

class DashboardRepository {

    @Inject
    lateinit var jobManager: JobManager

    init {
        Injector.obtain().inject(this)
    }

    fun getUsers(realm: Realm): LiveData<List<User>> {

        refreshDashBoard()

        return Transformations.map(findAllUsersForLeaderBoard(realm)) { realm.copyFromRealm(it) }
    }

    private fun refreshDashBoard() {
        jobManager.addJobInBackground(GetDashboardJob())
    }

}