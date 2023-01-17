package domain

import java.io.IOException
import java.nio.channels.UnresolvedAddressException

class FailureFactory {
    fun handle(e: Exception) =
        when (e) {
            is UnresolvedAddressException -> "No internet connection"
            is IOException -> "Service is unavailable"
            else -> "Invalid Request"
        }
}