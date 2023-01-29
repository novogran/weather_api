package com.example.weather_api.presentaion.common

import java.io.IOException
import java.nio.channels.UnresolvedAddressException

fun Exception.toErrorMessage(): String =
    when (this) {
        is UnresolvedAddressException -> "No internet connection"
        is IOException -> "Service is unavailable"
        else -> "Invalid Request"
    }
