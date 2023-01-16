package data

import kotlinx.serialization.SerialName

@kotlinx.serialization.Serializable
data class WeatherApiCurrentInfoServerModel(
    @SerialName("temp_c")
    private val temp: Double
) {
    fun getTemp(): Double = temp
}
