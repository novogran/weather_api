package domain.repo

import domain.entity.WeatherEntity

interface WeatherRepo {
    suspend fun getWeather(weatherLocationToSearch: String): WeatherEntity
}