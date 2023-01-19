package presentaion

import data.api.WeatherComApi
import common.AppDispatchers
import data.mapper.WeatherEntityMapper
import data.repo.WeatherRepoImpl
import domain.CommonItem
import domain.GetWeatherUseCase
import domain.repo.WeatherRepo
import kotlinx.coroutines.*

class WeatherFinder {

    companion object {
        private const val ENTER_LATITUDE_COORDINATE = "Введите широту:"
        private const val ENTER_LONGITUDE_COORDINATE = "Введите долготу:"
        private const val ENTER_TYPE_OF_SEARCH = "Выберите тип поиска погоды:\n1 - по координатам\n2 - по городу"
        private const val INVALID_ENTER = "Неверный ввод"
        private const val ENTER_CITY = "Введите город:"
    }

    private val httpClient = ServiceLocator().getClient()
    private val weatherRepo: WeatherRepo = WeatherRepoImpl(WeatherComApi(httpClient), WeatherEntityMapper())
    private val dispatcherIO = AppDispatchers()
    private val getWeatherUseCase = GetWeatherUseCase(weatherRepo, dispatcherIO)

    suspend fun findWeather() {

        println(ENTER_TYPE_OF_SEARCH)

        val option = try {
            readlnOrNull()?.toInt()
        } catch (e: Exception) {
            println(INVALID_ENTER)
            findWeather()
        }

        when (option) {
            1 -> {
                try {
                    println(ENTER_LATITUDE_COORDINATE)
                    val latitude = readlnOrNull()?.toDouble()
                    println(ENTER_LONGITUDE_COORDINATE)
                    val longitude = readlnOrNull()?.toDouble()
                    weatherMonitor("$latitude,$longitude")
                } catch (e: Exception) {
                    println(INVALID_ENTER)
                    findWeather()
                }
            }

            2 -> {
                println(ENTER_CITY)
                val city = readlnOrNull()
                weatherMonitor(city)
            }

            else -> {
                println(INVALID_ENTER)
                findWeather()
            }
        }
    }

    private suspend fun weatherMonitor(weatherLocationToSearch: String?) {
        when (val weatherModel = getWeatherUseCase.execute(weatherLocationToSearch)) {
            is CommonItem.Success -> {
                printTableInConsole(weatherModel.from())
                delay(15000)
                weatherMonitor(weatherLocationToSearch)
            }

            is CommonItem.Failed -> {
                val failureWeatherModel = weatherModel.from()
                println(failureWeatherModel.errorText)
                findWeather()
            }
        }
    }

    private fun printTableInConsole(weatherModel: BaseWeatherModel) {
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