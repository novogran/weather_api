package data

import domain.CommonItem

class CommonDataModel(
    private val locationName: String, private val countryName: String, private val locationTemperature: Double
) {
    fun map(): CommonItem {
        return CommonItem.Success(locationName, countryName, locationTemperature)
    }
}