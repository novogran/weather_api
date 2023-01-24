package data.dto

import kotlinx.serialization.SerialName

@kotlinx.serialization.Serializable
data class WeatherApiServerModel(
    @SerialName("location")
    val locationInfoServerModel: WeatherApiLocationInfoServerModel,
    @SerialName("current")
    val currentInfoServerModel: WeatherApiCurrentInfoServerModel
)