package domain

import Mapper
import presentaion.FailureWeatherModel
import presentaion.BaseWeatherModel

sealed interface CommonItem : Mapper<Any> {

    data class Success(
        val locationName: String,
        val countryName: String,
        val locationTemperature: Double
    ) : CommonItem {
        override fun to(): BaseWeatherModel {
            return BaseWeatherModel(locationName, countryName, locationTemperature)
        }
    }

    data class Failed(val failureText: String) : CommonItem {
        override fun to(): FailureWeatherModel {
            return FailureWeatherModel(failureText)
        }
    }
}