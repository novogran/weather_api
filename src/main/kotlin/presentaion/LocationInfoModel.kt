package presentaion

class LocationInfoModel(
    private val locationName: String,
    private val countryName: String,
    private val locationTemperature: Double
) {

    fun getLocationName(): String {
        return locationName
    }

    fun getCountryName(): String {
        return countryName
    }

    fun getLocationTemperature(): Double {
        return locationTemperature
    }

}