package domain

import domain.entity.WeatherEntity
import domain.repo.WeatherRepo
import javax.inject.Inject

class GetWeatherUseCaseImpl @Inject constructor(
    private val weatherRepo: WeatherRepo,
) {
    suspend fun execute(weatherLocationToSearch: String): WeatherEntity {
        return weatherRepo.getWeather(weatherLocationToSearch)
    }
}
