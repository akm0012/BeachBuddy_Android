package com.andrewkingmarshall.beachbuddy.job

import com.andrewkingmarshall.beachbuddy.database.realmObjects.CurrentWeather
import com.andrewkingmarshall.beachbuddy.database.realmObjects.User
import com.andrewkingmarshall.beachbuddy.eventbus.GetDashboardEvent
import com.andrewkingmarshall.beachbuddy.extensions.save
import com.andrewkingmarshall.beachbuddy.inject.AppComponent
import com.andrewkingmarshall.beachbuddy.network.dtos.DashboardDto
import com.andrewkingmarshall.beachbuddy.network.service.ApiService
import com.birbit.android.jobqueue.Params
import com.birbit.android.jobqueue.RetryConstraint
import org.greenrobot.eventbus.EventBus
import timber.log.Timber
import javax.inject.Inject

class GetDashboardJob : BaseJob(
    Params(UI_HIGH).requireNetwork()
        .singleInstanceBy(GetDashboardJob::class.simpleName)
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

        val dashboardDto = apiService.getDashboard(lat = 27.267804, lon = -82.553679).body()
            ?: return

        // Users
        val usersToSave = ArrayList<User>()
        for (userDto in dashboardDto.users) {
            try {
                usersToSave.add(User(userDto))
            } catch (e: Exception) {
                Timber.w(e, "Unable to process item. Skipping it. $userDto")
                continue
            }
        }

        // Current Weather Info
        try {
            val currentWeather = CurrentWeather(dashboardDto.weatherDto, dashboardDto.beachConditions)
            currentWeather.save()
        } catch (e: Exception) {
            Timber.w(e, "Unable to process item. Skipping it. $dashboardDto.weatherDto")
        }

        usersToSave.save()

        EventBus.getDefault().post(GetDashboardEvent(true))
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
        EventBus.getDefault().post(GetDashboardEvent(true, throwable))
    }
}