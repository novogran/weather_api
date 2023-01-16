package data

import kotlinx.serialization.SerialName

@kotlinx.serialization.Serializable
data class WeatherApiServerModel(
    @SerialName("location")
    private val weatherApiLocationInfoServerModel: WeatherApiLocationInfoServerModel,
    @SerialName("current")
    private val weatherApiCurrentInfoServerModel: WeatherApiCurrentInfoServerModel
) {
    fun getLocation(): WeatherApiLocationInfoServerModel = weatherApiLocationInfoServerModel

    fun getCurrentInfo(): WeatherApiCurrentInfoServerModel = weatherApiCurrentInfoServerModel
}