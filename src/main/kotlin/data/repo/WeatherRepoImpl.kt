package data.repo

import data.api.WeatherApi
import data.mapper.WeatherEntityMapper
import domain.entity.WeatherEntity
import domain.repo.WeatherRepo
import javax.inject.Inject

class WeatherRepoImpl @Inject constructor(
    private val api: WeatherApi,
    private val mapper: WeatherEntityMapper
) : WeatherRepo {
    override suspend fun getWeather(weatherLocationToSearch: String): WeatherEntity {
        val response = api.getWeather(weatherLocationToSearch)
        return mapper.map(response)
    }
}