package com.andrewkingmarshall.beachbuddy.database

import android.app.Application
import com.andrewkingmarshall.beachbuddy.database.realmObjects.RequestedItem
import io.realm.*
import org.joda.time.DateTime
import org.joda.time.DateTimeZone
import timber.log.Timber
import java.util.*

const val REALM_NAME = "beachbuddy.realm"

const val REALM_SCHEMA_VERSION = 0L

fun initRealm(context: Application) {
    Realm.init(context)
    Realm.setDefaultConfiguration(getRealmConfiguration())
}

fun getRealmConfiguration(): RealmConfiguration {
    val realmBuilder = RealmConfiguration.Builder().apply {
        name(REALM_NAME)
        schemaVersion(REALM_SCHEMA_VERSION)
        deleteRealmIfMigrationNeeded()
    }

    return realmBuilder.build()
}

fun clearRealm() {
    Timber.d("Clearing Realm...")
    val defaultConfiguration = Realm.getDefaultConfiguration()
    defaultConfiguration?.let {
        DynamicRealm.getInstance(it).run {
            executeTransaction { deleteAll() }
            close()
        }
    }
    Timber.d("Realm cleared.")
}

fun findAllRequestedNotCompletedItems(realm: Realm) : LiveRealmData<RequestedItem> {
    return LiveRealmData(realm.where(RequestedItem::class.java)
        .sort("createdAtTime", Sort.DESCENDING)
        .equalTo("isComplete", false)
        .findAllAsync())
}

fun findAllCompetedTodayRequestedItems(realm: Realm) : LiveRealmData<RequestedItem> {

    val tomorrowStartOfDay =
        DateTime(DateTime.now(DateTimeZone.getDefault())).plusDays(1)
            .withTimeAtStartOfDay().millis
    val todayStartOfDay =
        DateTime(DateTime.now(DateTimeZone.getDefault()))
            .withTimeAtStartOfDay().millis

    return LiveRealmData(realm.where(RequestedItem::class.java)
        .lessThan("completedAtTime", tomorrowStartOfDay)
        .greaterThanOrEqualTo("completedAtTime", todayStartOfDay)
        .equalTo("isComplete", true)
        .sort("completedAtTime", Sort.DESCENDING)
        .findAllAsync())
}

fun markRequestedItemAsComplete(requestedItemId: String) {
    val realm = Realm.getDefaultInstance()
    realm.executeTransaction {
        val itemToUpdate = it.where(RequestedItem::class.java).equalTo("id", requestedItemId).findFirst()
        if (itemToUpdate != null) {
            itemToUpdate.isComplete = true
            itemToUpdate.completedAtTime = DateTime.now().millis
        }
    }
    realm.close()
}

fun markRequestedItemAsNotCompleted(requestedItemId: String) {
    val realm = Realm.getDefaultInstance()
    realm.executeTransaction {
        val itemToUpdate = it.where(RequestedItem::class.java).equalTo("id", requestedItemId).findFirst()
        if (itemToUpdate != null) {
            itemToUpdate.isComplete = false
            itemToUpdate.completedAtTime = null
        }
    }
    realm.close()
}

/**
 * This will delete any Requested Item that is:
 * 1. Not Completed
 * 2. Not in the list of Valid Messages.
 */
fun purgeDeletedRequestedItems(
    validRequestedItems: List<RequestedItem>?
) {
    val realmOuter = Realm.getDefaultInstance()
    realmOuter.executeTransaction { realm: Realm ->
        val validItemIds =
            ArrayList<String>()
        for (item in validRequestedItems!!) {
            validItemIds.add(item.id)
        }
        val validItemIdsArray =
            validItemIds.toTypedArray()
        val invalidItems: RealmResults<RequestedItem>
        invalidItems = if (validItemIdsArray.isEmpty()) {
            // We need to delete all of them if there are no valid results.
            realm.where(RequestedItem::class.java)
                .equalTo("isComplete", false)
                .findAll()
        } else {
            realm.where(RequestedItem::class.java)
                .equalTo("isComplete", false)
                .not().`in`("id", validItemIdsArray)
                .findAll()
        }
        Timber.d("Purging ${invalidItems.size} Requested Items.")
        invalidItems.deleteAllFromRealm()
    }
    realmOuter.close()
}
