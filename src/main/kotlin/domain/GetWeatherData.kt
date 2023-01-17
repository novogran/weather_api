package domain

import data.CommonDataModel

interface GetWeatherData {
    suspend fun getWeather(weatherLocationToSearch: String): CommonDataModel
}