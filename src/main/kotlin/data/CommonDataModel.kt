package data

import Mapper
import domain.CommonItem

data class CommonDataModel(
    val locationName: String, val countryName: String, val locationTemperature: Double
) : Mapper<CommonItem> {
    override fun to(): CommonItem {
        return CommonItem.Success(locationName, countryName, locationTemperature)
    }
}