package com.andrewkingmarshall.beachbuddy.database.realmObjects

import com.andrewkingmarshall.beachbuddy.network.dtos.RequestedItemDto
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import org.joda.time.DateTime

open class RequestedItem(

    @PrimaryKey
    var id: String = "",

    var name: String = "",

    var count: Int = 0,

    var isComplete: Boolean = false,

    var createdAtTime: Long = 0L,

    var completedAtTime: Long? = null,

    var requestorId: String = "",

    var requestorFirstName: String = "",

    var requestorLastName: String = "",

    var requestorPhotoUrl: String = ""

) : RealmObject() {

    constructor(itemDto: RequestedItemDto) : this() {
        id = itemDto.id
        name = itemDto.name
        count = itemDto.count
        isComplete = itemDto.isRequestCompleted
        createdAtTime = DateTime(itemDto.createdDateTime).millis
        requestorId = itemDto.requestedByUserId
        requestorFirstName = itemDto.requestedByUser.firstName
        requestorLastName = itemDto.requestedByUser.lastName
        requestorPhotoUrl = itemDto.requestedByUser.photoUrl

        if (itemDto.completedDateTime != null) {
            completedAtTime = DateTime(itemDto.completedDateTime).millis
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as RequestedItem

        if (id != other.id) return false
        if (name != other.name) return false
        if (count != other.count) return false
        if (isComplete != other.isComplete) return false
        if (createdAtTime != other.createdAtTime) return false
        if (completedAtTime != other.completedAtTime) return false
        if (requestorId != other.requestorId) return false
        if (requestorFirstName != other.requestorFirstName) return false
        if (requestorLastName != other.requestorLastName) return false
        if (requestorPhotoUrl != other.requestorPhotoUrl) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + name.hashCode()
        result = 31 * result + count
        result = 31 * result + isComplete.hashCode()
        result = 31 * result + createdAtTime.hashCode()
        result = 31 * result + (completedAtTime?.hashCode() ?: 0)
        result = 31 * result + requestorId.hashCode()
        result = 31 * result + requestorFirstName.hashCode()
        result = 31 * result + requestorLastName.hashCode()
        result = 31 * result + requestorPhotoUrl.hashCode()
        return result
    }
}