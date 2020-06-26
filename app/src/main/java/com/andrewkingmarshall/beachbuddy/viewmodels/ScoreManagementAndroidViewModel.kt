package com.andrewkingmarshall.beachbuddy.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.andrewkingmarshall.beachbuddy.database.realmObjects.User
import com.andrewkingmarshall.beachbuddy.eventbus.GetDashboardEvent
import com.andrewkingmarshall.beachbuddy.inject.Injector
import com.andrewkingmarshall.beachbuddy.repository.DashboardRepository
import io.realm.Realm
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import javax.inject.Inject

class ScoreManagementAndroidViewModel(application: Application) : AndroidViewModel(application) {

    val showToast: SingleLiveEvent<String> = SingleLiveEvent()
    val showLoadingEvent: SingleLiveEvent<Boolean> = SingleLiveEvent()

    private val realm = Realm.getDefaultInstance()

    @Inject
    lateinit var dashboardRepository: DashboardRepository

    init {
        Injector.obtain().inject(this)
        EventBus.getDefault().register(this)
    }

    fun getUsers(): LiveData<List<User>> {
        return dashboardRepository.getUsers(realm)
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