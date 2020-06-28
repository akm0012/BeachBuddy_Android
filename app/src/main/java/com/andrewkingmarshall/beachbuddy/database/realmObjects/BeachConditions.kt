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

    var deadFish: String = "",

    var waterColor: String = "",

    var surf: String = "",

    var surfType: String = "",

    var surfHeight: String = "",

    var respiratoryIrritation: String = "",

    var waterTemp: String = "",

    var airTemp: String = "",

    var crowds: String = "",

    var jellyFish: String = "",

    var ripCurrent: String = "",

    var weatherSummary: String = "",

    var windSpeed: String = "",

    var redDrift: String = ""

): RealmObject() {

    constructor(beachConditionsDto: BeachConditionsDto): this() {

        val latest = beachConditionsDto.latest

        val formatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")
        val dateTime = formatter.parseDateTime(latest.timeUpdated).withZone(DateTimeZone.getDefault())
        timeUpdated = dateTime.millis

        flagColor = latest.flag
        deadFish = latest.deadfish
        waterColor = latest.waterColor
        surf = latest.surf
        surfType = latest.surfType
        surfHeight = latest.surfHeight
        respiratoryIrritation = latest.respiratoryIrritation
        waterTemp = latest.waterTemp
        airTemp = latest.airTemp
        crowds = latest.crowds
        jellyFish = latest.jellyFish
        ripCurrent = latest.ripCurrent
        weatherSummary = latest.weatherSummary
        windSpeed = latest.windSpeed
        redDrift = latest.redDrift
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
        if (deadFish != other.deadFish) return false
        if (waterColor != other.waterColor) return false
        if (surf != other.surf) return false
        if (surfType != other.surfType) return false
        if (surfHeight != other.surfHeight) return false
        if (respiratoryIrritation != other.respiratoryIrritation) return false
        if (waterTemp != other.waterTemp) return false
        if (airTemp != other.airTemp) return false
        if (crowds != other.crowds) return false
        if (jellyFish != other.jellyFish) return false
        if (ripCurrent != other.ripCurrent) return false
        if (weatherSummary != other.weatherSummary) return false
        if (windSpeed != other.windSpeed) return false
        if (redDrift != other.redDrift) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + timeUpdated.hashCode()
        result = 31 * result + flagColor.hashCode()
        result = 31 * result + deadFish.hashCode()
        result = 31 * result + waterColor.hashCode()
        result = 31 * result + surf.hashCode()
        result = 31 * result + surfType.hashCode()
        result = 31 * result + surfHeight.hashCode()
        result = 31 * result + respiratoryIrritation.hashCode()
        result = 31 * result + waterTemp.hashCode()
        result = 31 * result + airTemp.hashCode()
        result = 31 * result + crowds.hashCode()
        result = 31 * result + jellyFish.hashCode()
        result = 31 * result + ripCurrent.hashCode()
        result = 31 * result + weatherSummary.hashCode()
        result = 31 * result + windSpeed.hashCode()
        result = 31 * result + redDrift.hashCode()
        return result
    }
}