package domain

import kotlinx.coroutines.*
import kotlin.coroutines.EmptyCoroutineContext

class GetWeatherUseCase(private val getWeatherData: GetWeatherData, private val appDispatchers: AppDispatchers) {

    private val requestScope: CoroutineScope = CoroutineScope(EmptyCoroutineContext)
    suspend fun execute(weatherLocationToSearch: String?): CommonItem {
        return try {
            requestScope.async(appDispatchers.io) {
                getWeatherData.getWeather(weatherLocationToSearch)
            }.await().to()
        } catch (e: Exception) {
            CommonItem.Failed(FailureFactory().handle(e))
        }
    }
}