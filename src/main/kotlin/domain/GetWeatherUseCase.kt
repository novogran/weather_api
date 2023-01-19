package domain

import common.AppDispatchers
import domain.mapper.CommonItemMapper
import domain.repo.WeatherRepo
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import presentaion.model.CommonItem

class GetWeatherUseCase(
    private val weatherRepo: WeatherRepo,
    private val commonItemMapper: CommonItemMapper,
    private val appDispatchers: AppDispatchers
) {
    suspend fun execute(weatherLocationToSearch: String): CommonItem {

        val weatherEntity = withContext(appDispatchers.io) {
            async {
                weatherRepo.getWeather(weatherLocationToSearch)
            }.await()
        }
        return commonItemMapper.map(weatherEntity)
    }
}
