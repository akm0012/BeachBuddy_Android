package com.andrewkingmarshall.beachbuddy.eventbus

data class GetDashboardEvent(val isDone: Boolean, val error: Throwable? = null)