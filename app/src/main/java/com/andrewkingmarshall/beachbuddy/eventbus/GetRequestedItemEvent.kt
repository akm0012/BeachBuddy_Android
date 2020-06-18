package com.andrewkingmarshall.beachbuddy.eventbus

data class GetRequestedItemEvent(val isDone: Boolean, val error: Throwable? = null)