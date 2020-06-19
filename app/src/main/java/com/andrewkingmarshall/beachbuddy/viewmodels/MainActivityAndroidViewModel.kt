package com.andrewkingmarshall.beachbuddy.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.andrewkingmarshall.beachbuddy.inject.Injector
import com.andrewkingmarshall.beachbuddy.repository.FirebaseRepository
import com.andrewkingmarshall.beachbuddy.repository.RequestedItemRepository
import io.realm.Realm
import javax.inject.Inject

class MainActivityAndroidViewModel(application: Application) : AndroidViewModel(application) {

    @Inject
    lateinit var requestedItemRepository: RequestedItemRepository

    @Inject
    lateinit var firebaseRepository: FirebaseRepository

    private val realm = Realm.getDefaultInstance()

    init {
        Injector.obtain().inject(this)

        firebaseRepository.registerDevice()
    }

    fun getNumberOfNotCompletedRequestedItems(): LiveData<Int> {
        return Transformations.map(requestedItemRepository.getNotCompletedRequestedItems(realm)) {it.count()}
    }

    override fun onCleared() {
        super.onCleared()
        realm.close()
    }
}
