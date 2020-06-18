package com.andrewkingmarshall.beachbuddy.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.andrewkingmarshall.beachbuddy.database.realmObjects.RequestedItem
import com.andrewkingmarshall.beachbuddy.inject.Injector
import com.andrewkingmarshall.beachbuddy.repository.RequestedItemRepository
import io.realm.Realm
import javax.inject.Inject

class RequestedItemAndroidViewModel(application: Application) : AndroidViewModel(application) {

    @Inject
    lateinit var requestedItemRepository: RequestedItemRepository

    private val realm = Realm.getDefaultInstance()

    init {
        Injector.obtain().inject(this)
    }

    fun getRequestedItems(): LiveData<List<RequestedItem>> {
        return requestedItemRepository.getAllRequestedItems(realm)
    }

    fun onRequestedItemChecked(requestedItem: RequestedItem) {

    }

    override fun onCleared() {
        super.onCleared()
        realm.close()
    }
}