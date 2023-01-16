package domain

import presentaion.LocationInfoModel

interface GetWeatherData {
    suspend fun getWeather(weatherLocationToSearch: String): LocationInfoModel
}