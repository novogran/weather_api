package presentaion

import common.DaggerAppComponent
import common.toErrorMessage
import domain.GetWeatherUseCaseImpl
import kotlinx.coroutines.*
import presentaion.mapper.WeatherViewDataMapper
import presentaion.model.WeatherViewState
import javax.inject.Inject

class WeatherViewModel {

    companion object {
        private const val ENTER_LATITUDE_COORDINATE = "Введите широту:"
        private const val ENTER_LONGITUDE_COORDINATE = "Введите долготу:"
        private const val ENTER_TYPE_OF_SEARCH = "Выберите тип поиска погоды:\n1 - по координатам\n2 - по городу"
        private const val INVALID_ENTER = "Неверный ввод"
        private const val ENTER_CITY = "Введите город:"
    }

    private val appComponent = DaggerAppComponent.create()

    @Inject
    lateinit var getWeatherUseCase: GetWeatherUseCaseImpl

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
            appComponent.inject(this)
            val weatherEntity = getWeatherUseCase.execute(weatherLocationToSearch)
            val weatherData = WeatherViewDataMapper().map(weatherEntity)
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