package domain

import presentaion.LocationInfoModel

class GetWeatherUseCase(private val getWeatherData: GetWeatherData) {

    suspend fun execute(weatherLocationToSearch: String): LocationInfoModel {
        return getWeatherData.getWeather(weatherLocationToSearch)
    }

}