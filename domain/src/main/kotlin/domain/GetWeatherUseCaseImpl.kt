package domain

import GetWeatherUseCase
import domain.mapper.WeatherViewDataMapper
import domain.repo.WeatherRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import presentaion.entity.WeatherViewData
import javax.inject.Inject

class GetWeatherUseCaseImpl @Inject constructor(
    private val weatherRepo: WeatherRepo,
    private val commonItemMapper: WeatherViewDataMapper,
): GetWeatherUseCase {
    override suspend fun execute(weatherLocationToSearch: String): WeatherViewData {

        val weatherEntity = withContext(Dispatchers.IO) {
            async {
                weatherRepo.getWeather(weatherLocationToSearch)
            }.await()
        }
        return commonItemMapper.map(weatherEntity)
    }
}
