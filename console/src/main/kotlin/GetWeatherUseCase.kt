import presentaion.entity.WeatherViewData

interface GetWeatherUseCase {
    suspend fun execute(weatherLocationToSearch: String): WeatherViewData
}