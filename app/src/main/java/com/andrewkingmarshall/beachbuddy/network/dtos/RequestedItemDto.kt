package com.andrewkingmarshall.beachbuddy.network.dtos

data class RequestedItemDto(
    var id: String,

    var name: String,

    var count: Int,

    var requestedByUserId: String,

    var requestedByUser: UserDto,

    var isRequestCompleted: Boolean,

    var createdDateTime: String
)