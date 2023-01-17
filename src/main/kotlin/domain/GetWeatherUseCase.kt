package domain

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking

class GetWeatherUseCase(private val getWeatherData: GetWeatherData) {

    suspend fun execute(weatherLocationToSearch: String): CommonItem {
        return try {
            runBlocking(Dispatchers.IO) {
                async {
                    getWeatherData.getWeather(weatherLocationToSearch).map()
                }
            }.await()
        } catch (e: Exception) {
            CommonItem.Failed(FailureFactory().handle(e))
        }
    }
}