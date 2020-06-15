package com.andrewkingmarshall.beachbuddy.network.dtos

import com.andrewkingmarshall.beachbuddy.network.dtos.ScoreDto

data class UserDto(

    val id: String,

    val firstName: String,

    val lastName: String,

    val fullName: String,

    val skinType: Int,

    val phoneNumber: String,

    val photoUrl: String,

    val scores: List<ScoreDto>
)