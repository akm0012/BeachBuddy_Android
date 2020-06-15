package com.andrewkingmarshall.beachbuddy.exceptions

import java.io.IOException

class NetworkException(
    var errorMessage: String,
    var httpErrorCode: Int = 0
) : IOException(errorMessage) {

}