package com.andrewkingmarshall.beachbuddy.eventbus

data class PostUpdateRequestedItemEvent(val isDone: Boolean, val error: Throwable? = null)