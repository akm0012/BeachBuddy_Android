package com.andrewkingmarshall.beachbuddy.eventbus

data class PostUpdateScoreEvent(val isDone: Boolean, val error: Throwable? = null)