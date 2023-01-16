package presentaion

import data.GetWeatherDataImpl
import domain.GetWeatherUseCase
import kotlinx.coroutines.*

class WeatherFinder {

    companion object {
        private const val ENTER_LATITUDE_COORDINATE_TEXT = "Введите широту:"
        private const val ENTER_LONGITUDE_COORDINATE_TEXT = "Введите долготу:"
    }

    private val getWeatherData = GetWeatherDataImpl()
    private val getWeatherUseCase = GetWeatherUseCase(getWeatherData)
    private val inputValidation = InputValidation()

    suspend fun findWeather() {

        println("Выберите тип поиска погоды:\n1 - по координатам\n2 - по городу")

        when (inputValidation.checkInput(readlnOrNull())) {
            1 -> {
                val latitude = inputValidation.enterCoordinate(ENTER_LATITUDE_COORDINATE_TEXT)
                val longitude = inputValidation.enterCoordinate(ENTER_LONGITUDE_COORDINATE_TEXT)
                weatherMonitor("$latitude,$longitude")
            }

            2 -> {
                println("Введите город:")
                val city = readlnOrNull().toString()
                weatherMonitor(city)
            }

            else -> {
                println("Неверный ввод")
                findWeather()
            }
        }
    }

    private suspend fun weatherMonitor(weatherLocationToSearch: String) {
        do {

            val locationInfoModel: LocationInfoModel = runBlocking(Dispatchers.IO) {
                async {
                    getWeatherUseCase.execute(weatherLocationToSearch)
                }
            }.await()

            printResultInConsole(locationInfoModel)
            delay(15000)
        } while (true)
    }

    private fun printResultInConsole(locationInfoModel: LocationInfoModel) {
        val line = "------------------------------"
        println(
            "Локация\t\t|\t${locationInfoModel.getLocationName()}\n$line\nСтрана\t\t|\t${
                locationInfoModel.getCountryName()
            }\n$line\nТемпература\t|\t${
                locationInfoModel.getLocationTemperature()
            }°C"
        )
    }
}