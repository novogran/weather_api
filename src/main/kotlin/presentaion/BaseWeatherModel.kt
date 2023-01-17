package presentaion

import domain.CommonWeatherModel

class BaseWeatherModel(
    private val locationName: String,
    private val countryName: String,
    private val locationTemperature: Double
) : CommonWeatherModel() {

    fun getLocationName(): String {
        return locationName
    }

    fun getCountryName(): String {
        return countryName
    }

    fun getLocationTemperature(): Double {
        return locationTemperature
    }

    override fun text() = "$locationName\n$countryName\n$locationTemperature"
}