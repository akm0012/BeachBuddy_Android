package com.andrewkingmarshall.beachbuddy.network

/**
 * This relays the status of Network calls.
 */
abstract class NetworkCallStatusListener<T> {
    open fun onStarted() {}
    open fun onCompleted(responseObject: T) {}
    open fun onErrorOccurred(error: Throwable?) {}
}