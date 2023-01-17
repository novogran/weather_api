package presentaion

import data.GetWeatherDataImpl
import domain.GetWeatherUseCase
import kotlinx.coroutines.*

class WeatherFinder {

    companion object {
        private const val ENTER_LATITUDE_COORDINATE = "Введите широту:"
        private const val ENTER_LONGITUDE_COORDINATE = "Введите долготу:"
        private const val ENTER_TYPE_OF_SEARCH = "Выберите тип поиска погоды:\n1 - по координатам\n2 - по городу"
        private const val INVALID_ENTER = "Неверный ввод"
        private const val ENTER_CITY = "Введите город:"
    }

    private val serviceLocator = ServiceLocator().getClient()
    private val getWeatherData = GetWeatherDataImpl(serviceLocator)
    private val getWeatherUseCase = GetWeatherUseCase(getWeatherData)

    suspend fun findWeather() {

        println(ENTER_TYPE_OF_SEARCH)

        when (readLine()?.toInt()) {
            1 -> {
                try {
                    println(ENTER_LATITUDE_COORDINATE)
                    val latitude = readLine()?.toDouble()
                    println(ENTER_LONGITUDE_COORDINATE)
                    val longitude = readLine()?.toDouble()
                    weatherMonitor("$latitude,$longitude")
                }catch (e:Exception){
                    println(INVALID_ENTER)
                    findWeather()
                }
            }

            2 -> {
                println(ENTER_CITY)
                val city = readlnOrNull().toString()
                weatherMonitor(city)
            }

            else -> {
                println(INVALID_ENTER)
                findWeather()
            }
        }
    }

    private suspend fun weatherMonitor(weatherLocationToSearch: String) {
        do {
            val weatherModel = getWeatherUseCase.execute(weatherLocationToSearch).to()

            if (weatherModel is BaseWeatherModel) {
                printResultInConsole(weatherModel)
            } else {
                println(weatherModel.text())
                findWeather()
            }

            delay(15000)

        } while (true)
    }

    private fun printResultInConsole(weatherModel: BaseWeatherModel) {
        val line = "----------------------------------"
        println(
            "$line\n|\tЛокация\t\t|\t${weatherModel.getLocationName()}\n$line\n|\tСтрана\t\t|\t${
                weatherModel.getCountryName()
            }\n$line\n|\tТемпература\t|\t${
                weatherModel.getLocationTemperature()
            }°C"
        )
    }
}