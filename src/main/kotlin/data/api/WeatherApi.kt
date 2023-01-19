package data.api

import data.dto.WeatherApiServerModel

interface WeatherApi {
    suspend fun getWeather(weatherLocationToSearch: String): WeatherApiServerModel
}