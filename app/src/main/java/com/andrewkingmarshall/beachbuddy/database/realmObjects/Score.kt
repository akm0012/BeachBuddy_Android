package com.andrewkingmarshall.beachbuddy.database.realmObjects

import com.andrewkingmarshall.beachbuddy.network.dtos.ScoreDto
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class Score(

    @PrimaryKey
    var id: String = "",

    var name: String = "",

    var winCount: Int = 0,

    var userId: String = ""

): RealmObject() {

    constructor(scoreDto: ScoreDto) : this() {
        id = scoreDto.id
        name = scoreDto.name
        winCount = scoreDto.winCount
        userId = scoreDto.userId
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Score

        if (id != other.id) return false
        if (name != other.name) return false
        if (winCount != other.winCount) return false
        if (userId != other.userId) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + name.hashCode()
        result = 31 * result + winCount
        result = 31 * result + userId.hashCode()
        return result
    }
}