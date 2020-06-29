package com.andrewkingmarshall.beachbuddy.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.andrewkingmarshall.beachbuddy.database.realmObjects.*
import com.andrewkingmarshall.beachbuddy.eventbus.GetDashboardEvent
import com.andrewkingmarshall.beachbuddy.eventbus.GetRequestedItemEvent
import com.andrewkingmarshall.beachbuddy.eventbus.PostUpdateRequestedItemEvent
import com.andrewkingmarshall.beachbuddy.inject.Injector
import com.andrewkingmarshall.beachbuddy.repository.DashboardRepository
import com.andrewkingmarshall.beachbuddy.repository.RequestedItemRepository
import io.realm.Realm
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import javax.inject.Inject

class DashboardAndroidViewModel(application: Application) : AndroidViewModel(application) {

    val showToast: SingleLiveEvent<String> = SingleLiveEvent()
    val showLoadingEvent: SingleLiveEvent<Boolean> = SingleLiveEvent()

    @Inject
    lateinit var dashboardRepository: DashboardRepository

    private val realm = Realm.getDefaultInstance()

    init {
        Injector.obtain().inject(this)
        EventBus.getDefault().register(this)
    }

    fun getUsers(): LiveData<List<User>> {
        return dashboardRepository.getUsers(realm)
    }

    fun getCurrentWeather(): LiveData<CurrentWeather?> {
        return dashboardRepository.getCurrentWeather(realm)
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