package com.andrewkingmarshall.beachbuddy.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.andrewkingmarshall.beachbuddy.database.realmObjects.RequestedItem
import com.andrewkingmarshall.beachbuddy.eventbus.GetRequestedItemEvent
import com.andrewkingmarshall.beachbuddy.inject.Injector
import com.andrewkingmarshall.beachbuddy.repository.RequestedItemRepository
import io.realm.Realm
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import javax.inject.Inject

class RequestedItemAndroidViewModel(application: Application) : AndroidViewModel(application) {

    val showLoadingEvent: SingleLiveEvent<Boolean> = SingleLiveEvent()

    @Inject
    lateinit var requestedItemRepository: RequestedItemRepository

    private val realm = Realm.getDefaultInstance()

    init {
        Injector.obtain().inject(this)
        EventBus.getDefault().register(this)
    }

    fun getRequestedItems(): LiveData<List<RequestedItem>> {
        return requestedItemRepository.getAllRequestedItems(realm)
    }

    fun onRequestedItemChecked(requestedItem: RequestedItem) {

    }

    fun onPullToRefresh() {
        requestedItemRepository.refreshRequestedItems()
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onGetRecentActivityStatusEvent(event: GetRequestedItemEvent) {
        if (event.isDone || event.error != null) {
            showLoadingEvent.value = false
        }
    }

    override fun onCleared() {
        super.onCleared()
        realm.close()
        EventBus.getDefault().unregister(this)
    }
}