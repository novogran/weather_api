package domain

import Mapper
import presentaion.FailureWeatherModel
import presentaion.BaseWeatherModel

sealed class CommonItem : Mapper<CommonWeatherModel> {

    class Success(
        private val locationName: String,
        private val countryName: String,
        private val locationTemperature: Double
    ) : CommonItem() {
        override fun to(): CommonWeatherModel {
            return BaseWeatherModel(locationName, countryName, locationTemperature)
        }
    }

    class Failed(private val failureText: String) : CommonItem() {
        override fun to(): CommonWeatherModel {
            return FailureWeatherModel(failureText)
        }
    }
}