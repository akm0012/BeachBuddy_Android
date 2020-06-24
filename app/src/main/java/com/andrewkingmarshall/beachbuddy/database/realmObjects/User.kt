package com.andrewkingmarshall.beachbuddy.database.realmObjects

import com.andrewkingmarshall.beachbuddy.network.dtos.UserDto
import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class User(

    @PrimaryKey
    var id: String = "",

    var firstName: String = "",

    var lastName: String = "",

    var fullName: String = "",

    var skinType: Int = 1,

    var phoneNumber: String = "",

    var photoUrl: String = "",

    var totalScore: Int = 0,

    var scores: RealmList<Score> = RealmList()

) : RealmObject() {

    constructor(userDto: UserDto) : this() {
        id = userDto.id
        firstName = userDto.firstName
        lastName = userDto.lastName
        fullName = userDto.fullName
        skinType = userDto.skinType
        phoneNumber = userDto.phoneNumber
        photoUrl = userDto.photoUrl

        for (scoreDto in userDto.scores) {
            totalScore += scoreDto.winCount
            scores.add(Score(scoreDto))
        }
    }


    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as User

        if (id != other.id) return false
        if (firstName != other.firstName) return false
        if (lastName != other.lastName) return false
        if (fullName != other.fullName) return false
        if (skinType != other.skinType) return false
        if (phoneNumber != other.phoneNumber) return false
        if (photoUrl != other.photoUrl) return false
        if (scores != other.scores) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + firstName.hashCode()
        result = 31 * result + lastName.hashCode()
        result = 31 * result + fullName.hashCode()
        result = 31 * result + skinType
        result = 31 * result + phoneNumber.hashCode()
        result = 31 * result + photoUrl.hashCode()
        result = 31 * result + scores.hashCode()
        return result
    }
}