package domain

import common.AppDispatchers
import domain.mapper.WeatherViewDataMapper
import domain.repo.WeatherRepo
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import presentaion.entity.WeatherViewData

class GetWeatherUseCase(
    private val weatherRepo: WeatherRepo,
    private val commonItemMapper: WeatherViewDataMapper,
    private val dispatcher: AppDispatchers
) {
    suspend fun execute(weatherLocationToSearch: String): WeatherViewData {

        val weatherEntity = withContext(dispatcher.io) {
            async {
                weatherRepo.getWeather(weatherLocationToSearch)
            }.await()
        }
        return commonItemMapper.map(weatherEntity)
    }
}
