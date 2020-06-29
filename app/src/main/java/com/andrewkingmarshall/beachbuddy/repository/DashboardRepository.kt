package com.andrewkingmarshall.beachbuddy.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.andrewkingmarshall.beachbuddy.database.*
import com.andrewkingmarshall.beachbuddy.database.realmObjects.*
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

    fun getSunsetInfo(realm: Realm): LiveData<SunsetInfo?> {

        // Note: Not refreshing Dashboard as of now
        return Transformations.map(findCurrentSunsetInfo(realm)) {
            if (it.isNotEmpty()) {
                realm.copyFromRealm(it.first())
            } else {
                null
            }
        }
    }

    fun getCurrentWeather(realm: Realm): LiveData<CurrentWeather?> {

        // Note: Not refreshing Dashboard as of now
        return Transformations.map(findCurrentWeather(realm)) {
            if (it.isNotEmpty()) {
                realm.copyFromRealm(it.first())
            } else {
                null
            }
        }
    }

    fun getHourlyWeatherInfo(realm: Realm): LiveData<List<HourlyWeatherInfo>> {

        // Note: Not refreshing Dashboard as of now
        return Transformations.map(findHourlyWeatherInfos(realm)) { realm.copyFromRealm(it) }
    }

    fun getDailyWeatherInfo(realm: Realm): LiveData<List<DailyWeatherInfo>> {

        // Note: Not refreshing Dashboard as of now
        return Transformations.map(findDailyWeatherInfos(realm)) { realm.copyFromRealm(it) }
    }

    fun refreshDashBoard() {
        jobManager.addJobInBackground(GetDashboardJob())
    }

}