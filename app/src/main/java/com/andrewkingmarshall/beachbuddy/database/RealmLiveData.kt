package com.andrewkingmarshall.beachbuddy.database

import androidx.lifecycle.MutableLiveData
import io.realm.RealmChangeListener
import io.realm.RealmModel
import io.realm.RealmResults
import timber.log.Timber

/**
 * This is a wrapper for the RealmResults to expose them as Lifecycle aware LiveData.
 */
class LiveRealmData<T : RealmModel>(private val results: RealmResults<T>) : MutableLiveData<RealmResults<T>>() {
    private val listener = RealmChangeListener<RealmResults<T>> { this.setValue(it) }

    override fun onActive() {
        Timber.v("onActive: Adding Change Listener.")
        results.addChangeListener(listener)
        value = results // Force a change call to be made...
        // May want to add some logic to only update UI if a change really did occur,
        // this is used to fix an issue where data was loaded and the change listener was not active
    }

    override fun onInactive() {
        Timber.v("onInactive: Removing Change Listener.")
        results.removeChangeListener(listener)
    }
}