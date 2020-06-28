package com.andrewkingmarshall.beachbuddy.database.realmObjects

import com.andrewkingmarshall.beachbuddy.network.dtos.WeatherInfoDto
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import org.joda.time.DateTime
import org.joda.time.DateTimeZone

open class SunsetInfo(

    @PrimaryKey
    var id: String = "SunsetInfo",

    var sunrise: Long = 0,

    var sunset: Long = 0,

    var sunriseNextDay: Long = 0,

    var sunsetPrevDay: Long = 0

) : RealmObject() {

    constructor(weatherInfoDto: WeatherInfoDto) : this() {

        sunrise =
            DateTime(weatherInfoDto.current.sunrise * 1000).withZone(DateTimeZone.getDefault()).millis

        sunset =
            DateTime(weatherInfoDto.current.sunset * 1000).withZone(DateTimeZone.getDefault()).millis

        // Fixme: could make this more accurate by not assuming sunset/sunrise times
        sunriseNextDay = DateTime(sunrise).plusDays(1).withZone(DateTimeZone.getDefault()).millis
        sunsetPrevDay = DateTime(sunset).minusDays(1).withZone(DateTimeZone.getDefault()).millis
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as SunsetInfo

        if (id != other.id) return false
        if (sunrise != other.sunrise) return false
        if (sunset != other.sunset) return false
        if (sunriseNextDay != other.sunriseNextDay) return false
        if (sunsetPrevDay != other.sunsetPrevDay) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + sunrise.hashCode()
        result = 31 * result + sunset.hashCode()
        result = 31 * result + sunriseNextDay.hashCode()
        result = 31 * result + sunsetPrevDay.hashCode()
        return result
    }
}