package com.andrewkingmarshall.beachbuddy.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.andrewkingmarshall.beachbuddy.database.realmObjects.*
import com.andrewkingmarshall.beachbuddy.eventbus.GetDashboardEvent
import com.andrewkingmarshall.beachbuddy.inject.Injector
import com.andrewkingmarshall.beachbuddy.repository.DashboardRepository
import io.realm.Realm
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.joda.time.DateTime
import org.joda.time.DateTimeZone
import timber.log.Timber
import javax.inject.Inject

const val AUTO_UPDATE_PRIME_TIME_COOLDOWN_MIN = 15
const val AUTO_UPDATE_IDLE_COOLDOWN_MIN = 60

class DashboardAndroidViewModel(application: Application) : AndroidViewModel(application) {

    val showToast: SingleLiveEvent<String> = SingleLiveEvent()
    val showLoadingEvent: SingleLiveEvent<Boolean> = SingleLiveEvent()

    private var lastDashboardRefresh = 0L

    @Inject
    lateinit var dashboardRepository: DashboardRepository

    private val realm = Realm.getDefaultInstance()

    init {
        Injector.obtain().inject(this)
        EventBus.getDefault().register(this)
    }

    fun getUsers(): LiveData<List<User>> {
        lastDashboardRefresh = System.currentTimeMillis()
        return dashboardRepository.getUsers(realm)
    }

    fun getCurrentWeather(): LiveData<CurrentWeather?> {
        return dashboardRepository.getCurrentWeather(realm)
    }

    fun getCurrentUvIndex(): LiveData<CurrentUvInfo?> {
        return dashboardRepository.getCurrentUvIndex(realm)
    }

    fun getHourlyWeatherInfo(): LiveData<List<HourlyWeatherInfo>> {
        return dashboardRepository.getHourlyWeatherInfo(realm)
    }

    fun getDailyWeatherInfo(): LiveData<List<DailyWeatherInfo>> {
        return dashboardRepository.getDailyWeatherInfo(realm)
    }

    fun getSunsetInfo(): LiveData<SunsetInfo?> {
        return dashboardRepository.getSunsetInfo(realm)
    }

    fun onPullToRefresh() {
        lastDashboardRefresh = System.currentTimeMillis()
        dashboardRepository.refreshDashBoard()
    }

    fun onAutoUpdatePeriodHit() {

        Timber.v("Auto Update Period hit.")

        val currentTime = System.currentTimeMillis()
        var shouldAutoUpdate = false

        if (lastDashboardRefresh == 0L) {
            Timber.d("We have not auto updated yet. Planning to update.")
            shouldAutoUpdate = true
        } else {

            val currentDateTime = DateTime(currentTime).withZone(DateTimeZone.getDefault())

            // If we are in "Prime Time" (07:00 - 22:00) update every 15 min
            if (currentDateTime.isAfter(DateTime().withHourOfDay(7).withMinuteOfHour(0)) &&
                currentDateTime.isBefore(DateTime().withHourOfDay(22).withMinuteOfHour(0))
            ) {
                Timber.d("We are in Prime Time!")
                // If it has been 15 min since last update.
                if (currentDateTime.isAfter(
                        DateTime(lastDashboardRefresh).plusMinutes(
                            AUTO_UPDATE_PRIME_TIME_COOLDOWN_MIN
                        )
                    )
                ) {
                    Timber.d("The Prime Time Cooldown of $AUTO_UPDATE_PRIME_TIME_COOLDOWN_MIN minutes is over. Planning to Update.")
                    shouldAutoUpdate = true
                } else {
                    Timber.d("The Prime Time Cooldown of $AUTO_UPDATE_PRIME_TIME_COOLDOWN_MIN minutes is still in effect. Not updating.")
                }

            } else {
                // Update every hour
                Timber.d("We are in ideal Time")
                // If it has been an hour
                if (currentDateTime.isAfter(
                        DateTime(lastDashboardRefresh).plusMinutes(
                            AUTO_UPDATE_IDLE_COOLDOWN_MIN
                        )
                    )
                ) {
                    Timber.d("The Idle Time Cooldown of $AUTO_UPDATE_IDLE_COOLDOWN_MIN minutes is over. Planning to Update.")
                    shouldAutoUpdate = true
                } else {
                    Timber.d("The Idle Time Cooldown of $AUTO_UPDATE_IDLE_COOLDOWN_MIN minutes is still in effect. Not updating.")
                }
            }
        }

        if (shouldAutoUpdate) {
            Timber.i("Auto updating...")
            lastDashboardRefresh = currentTime
            dashboardRepository.refreshDashBoard()
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onGetDashboardEvent(event: GetDashboardEvent) {
        if (event.isDone || event.error != null) {
            showLoadingEvent.value = false
        }

        if (event.error != null) {
            showToast.value = event.error.localizedMessage
        }
    }

    override fun onCleared() {
        super.onCleared()
        realm.close()
        EventBus.getDefault().unregister(this)
    }
}