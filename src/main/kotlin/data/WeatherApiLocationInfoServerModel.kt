package data

import kotlinx.serialization.SerialName

@kotlinx.serialization.Serializable
data class WeatherApiLocationInfoServerModel(
    @SerialName("name")
    private val name: String,
    @SerialName("country")
    private val country: String,
) {
    fun getName() = name

    fun getCountryName() = country

}
