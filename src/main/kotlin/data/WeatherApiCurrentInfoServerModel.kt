package data

import kotlinx.serialization.SerialName

@kotlinx.serialization.Serializable
data class WeatherApiCurrentInfoServerModel(
    @SerialName("temp_c")
    val temp: Double
)
