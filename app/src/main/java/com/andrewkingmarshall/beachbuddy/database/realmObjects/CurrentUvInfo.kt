package com.andrewkingmarshall.beachbuddy.database.realmObjects

import com.andrewkingmarshall.beachbuddy.network.dtos.CurrentUvDto
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class CurrentUvInfo(

    @PrimaryKey
    var id:String = "CurrentUvInfo.PrimaryKey",

    var currentUv: Double = 0.0,

    var currentUvTime: String = "",

    var maxUv: Double = 0.0,

    var maxUvTime: String = "",

    var safeExposureMinSkin1: Int? = null,

    var safeExposureMinSkin2: Int? = null,

    var safeExposureMinSkin3: Int? = null,

    var safeExposureMinSkin4: Int? = null,

    var safeExposureMinSkin5: Int? = null,

    var safeExposureMinSkin6: Int? = null

): RealmObject() {

    constructor(uvDto: CurrentUvDto): this() {

        currentUv = uvDto.uv
        currentUvTime = uvDto.uvTime
        maxUv = uvDto.uvMax
        maxUvTime = uvDto.uvMaxTime
        safeExposureMinSkin1 = uvDto.safeExposureTime.st1
        safeExposureMinSkin2 = uvDto.safeExposureTime.st2
        safeExposureMinSkin3 = uvDto.safeExposureTime.st3
        safeExposureMinSkin4 = uvDto.safeExposureTime.st4
        safeExposureMinSkin5 = uvDto.safeExposureTime.st5
        safeExposureMinSkin6 = uvDto.safeExposureTime.st6
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as CurrentUvInfo

        if (id != other.id) return false
        if (currentUv != other.currentUv) return false
        if (currentUvTime != other.currentUvTime) return false
        if (maxUv != other.maxUv) return false
        if (maxUvTime != other.maxUvTime) return false
        if (safeExposureMinSkin1 != other.safeExposureMinSkin1) return false
        if (safeExposureMinSkin2 != other.safeExposureMinSkin2) return false
        if (safeExposureMinSkin3 != other.safeExposureMinSkin3) return false
        if (safeExposureMinSkin4 != other.safeExposureMinSkin4) return false
        if (safeExposureMinSkin5 != other.safeExposureMinSkin5) return false
        if (safeExposureMinSkin6 != other.safeExposureMinSkin6) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + currentUv.hashCode()
        result = 31 * result + currentUvTime.hashCode()
        result = 31 * result + maxUv.hashCode()
        result = 31 * result + maxUvTime.hashCode()
        result = 31 * result + (safeExposureMinSkin1 ?: 0)
        result = 31 * result + (safeExposureMinSkin2 ?: 0)
        result = 31 * result + (safeExposureMinSkin3 ?: 0)
        result = 31 * result + (safeExposureMinSkin4 ?: 0)
        result = 31 * result + (safeExposureMinSkin5 ?: 0)
        result = 31 * result + (safeExposureMinSkin6 ?: 0)
        return result
    }


}