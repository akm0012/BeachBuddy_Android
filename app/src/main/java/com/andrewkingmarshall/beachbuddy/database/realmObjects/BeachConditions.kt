package com.andrewkingmarshall.beachbuddy.database.realmObjects

import com.andrewkingmarshall.beachbuddy.enums.FlagColor
import com.andrewkingmarshall.beachbuddy.enums.getFlagColorEnum
import com.andrewkingmarshall.beachbuddy.network.dtos.BeachConditionsDto
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import org.joda.time.DateTimeZone
import org.joda.time.format.DateTimeFormat


open class BeachConditions(

    @PrimaryKey
    var id: String = "BeachConditionsPrimaryKey",

    // Millis
    var timeUpdated: Long = 0,

    var flagColor: String = "Green",

    var surfCondition: String = "",

    var surfHeight: String = "",

    var respiratoryIrritation: String = "",

    var jellyFish: String = "",

): RealmObject() {

    constructor(beachConditionsDto: BeachConditionsDto): this() {

        val formatter = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ssZ") // Example: "2021-04-04T13:56:23+00:00"
        val dateTime = formatter.parseDateTime(beachConditionsDto.updatedTime).withZone(DateTimeZone.UTC)
        timeUpdated = dateTime.millis

        flagColor = beachConditionsDto.flagColor
        surfCondition = beachConditionsDto.surfCondition
        surfHeight = beachConditionsDto.surfHeight
        respiratoryIrritation = beachConditionsDto.respiratoryIrritation
        jellyFish = beachConditionsDto.jellyFishPresent
    }

    fun getFlagColorEnum() : FlagColor {
        return flagColor.getFlagColorEnum()
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as BeachConditions

        if (id != other.id) return false
        if (timeUpdated != other.timeUpdated) return false
        if (flagColor != other.flagColor) return false
        if (surfCondition != other.surfCondition) return false
        if (surfHeight != other.surfHeight) return false
        if (respiratoryIrritation != other.respiratoryIrritation) return false
        if (jellyFish != other.jellyFish) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + timeUpdated.hashCode()
        result = 31 * result + flagColor.hashCode()
        result = 31 * result + surfCondition.hashCode()
        result = 31 * result + surfHeight.hashCode()
        result = 31 * result + respiratoryIrritation.hashCode()
        result = 31 * result + jellyFish.hashCode()
        return result
    }
}