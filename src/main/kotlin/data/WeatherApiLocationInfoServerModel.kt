package data

import kotlinx.serialization.SerialName

@kotlinx.serialization.Serializable
data class WeatherApiLocationInfoServerModel(
    @SerialName("name")
    val name: String,
    @SerialName("country")
    val country: String,
)
