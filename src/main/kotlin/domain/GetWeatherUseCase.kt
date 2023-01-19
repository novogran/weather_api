package domain

import common.AppDispatchers
import domain.repo.WeatherRepo
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext

class GetWeatherUseCase(
    private val getWeatherData: WeatherRepo,
    private val appDispatchers: AppDispatchers
) {

    suspend fun execute(weatherLocationToSearch: String?): CommonItem {
        return try {
            withContext(appDispatchers.io) {
                async {
                    getWeatherData.getWeather(weatherLocationToSearch)
                }.await().from()
            }
        } catch (e: Exception) {
            CommonItem.Failed(FailureFactory().handle(e))
        }
    }
}