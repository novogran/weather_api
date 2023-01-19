package presentaion

import data.api.WeatherComApi
import common.AppDispatchers
import common.HttpClient
import common.toErrorMessage
import data.mapper.WeatherEntityMapper
import data.repo.WeatherRepoImpl
import domain.GetWeatherUseCase
import domain.mapper.WeatherViewDataMapper
import domain.repo.WeatherRepo
import kotlinx.coroutines.*
import presentaion.model.WeatherViewState

class WeatherViewModel {

    companion object {
        private const val ENTER_LATITUDE_COORDINATE = "Введите широту:"
        private const val ENTER_LONGITUDE_COORDINATE = "Введите долготу:"
        private const val ENTER_TYPE_OF_SEARCH = "Выберите тип поиска погоды:\n1 - по координатам\n2 - по городу"
        private const val INVALID_ENTER = "Неверный ввод"
        private const val ENTER_CITY = "Введите город:"
    }

    private val httpClient = HttpClient().getClient()
    private val weatherRepo: WeatherRepo = WeatherRepoImpl(WeatherComApi(httpClient), WeatherEntityMapper())
    private val dispatcherIO = AppDispatchers()
    private val getWeatherUseCase = GetWeatherUseCase(weatherRepo, WeatherViewDataMapper(), dispatcherIO)

    suspend fun findWeather() {

        println(ENTER_TYPE_OF_SEARCH)

        val option = try {
            readlnOrNull()?.toInt()
        } catch (e: Exception) {
            println(INVALID_ENTER)
            findWeather()
        }

        if (option !in 1..2) {
            println(INVALID_ENTER)
            findWeather()
        }

        val param = if (option == 1) {
            try {
                println(ENTER_LATITUDE_COORDINATE)
                val latitude = readlnOrNull()?.toDouble()
                println(ENTER_LONGITUDE_COORDINATE)
                val longitude = readlnOrNull()?.toDouble()
                "$latitude,$longitude"
            } catch (e: Exception) {
                println(INVALID_ENTER)
                findWeather()
            }
        } else {

            println(ENTER_CITY)
            val city = readln()
            city
        }

        while (true) {
            weatherMonitor(param.toString())
            delay(3000)
        }
    }

    private suspend fun weatherMonitor(weatherLocationToSearch: String) {
        val weatherViewState = try {
            val weatherData = getWeatherUseCase.execute(weatherLocationToSearch)
            WeatherViewState.Success(weatherData.locationName, weatherData.countryName, weatherData.locationTemperature)
        } catch (e: Exception) {
            WeatherViewState.Failed(e.toErrorMessage())
        }

        when (weatherViewState) {
            is WeatherViewState.Success -> {
                printTableInConsole(weatherViewState)
            }

            is WeatherViewState.Failed -> {
                println(weatherViewState.failureText)
            }
        }
    }

    private fun printTableInConsole(weatherModel: WeatherViewState.Success) {
        val line = "----------------------------------"
        println(
            "$line\n|\tЛокация\t\t|\t${weatherModel.locationName}\n$line\n|\tСтрана\t\t|\t${
                weatherModel.countryName
            }\n$line\n|\tТемпература\t|\t${
                weatherModel.locationTemperature
            }°C"
        )
    }
}