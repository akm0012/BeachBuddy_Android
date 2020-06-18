package com.andrewkingmarshall.beachbuddy.ui.views.viewmodels

import android.content.Context
import com.andrewkingmarshall.beachbuddy.R
import com.andrewkingmarshall.beachbuddy.database.realmObjects.RequestedItem
import com.andrewkingmarshall.beachbuddy.inject.Injector
import org.joda.time.DateTime
import javax.inject.Inject

class RequestedItemViewModel(private val requestedItem: RequestedItem) {

    @Inject
    lateinit var context: Context

    init {
        Injector.obtain().inject(this)
    }

    fun getTitle(): String {
        return if (requestedItem.count > 1) {
            "${requestedItem.name} (${requestedItem.count})"
        } else {
            requestedItem.name
        }
    }

    fun getSubTitle(): String {

        val dayAndTime = DateTime(requestedItem.createdAtTime).toString("EE h:mm a")

        return "${requestedItem.requestorFirstName} • $dayAndTime"
    }

    fun getProfilePhotoUrl(): String {
        return "${context.getString(R.string.base_endpoint)}${requestedItem.requestorPhotoUrl}"
    }

    fun isChecked(): Boolean {
        return requestedItem.isComplete
    }
}