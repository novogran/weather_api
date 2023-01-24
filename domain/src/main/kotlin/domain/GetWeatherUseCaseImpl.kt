package domain

import domain.entity.WeatherEntity
import domain.repo.WeatherRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetWeatherUseCaseImpl @Inject constructor(
    private val weatherRepo: WeatherRepo,
) {
    suspend fun execute(weatherLocationToSearch: String): WeatherEntity {

        return withContext(Dispatchers.IO) {
            async {
                weatherRepo.getWeather(weatherLocationToSearch)
            }.await()
        }
    }
}
