package data

import domain.GetWeatherData
import presentaion.LocationInfoModel
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

class GetWeatherDataImpl : GetWeatherData {

    override suspend fun getWeather(weatherLocationToSearch: String): LocationInfoModel {

        val locationInfoModel: LocationInfoModel

        val client = HttpClient(CIO) {
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                })
            }
        }

        val response: HttpResponse = client.get{
            url{
                protocol = URLProtocol.HTTP
                host = "api.weatherapi.com"
                path("v1/current.json")
                parameter("key", "69e6d334e8c5405fbe4132858231001")
                parameter("q",weatherLocationToSearch)
            }
        }
        client.close()

        locationInfoModel = if (checkResponseStatus(response)) {
            val weatherData: WeatherApiServerModel = response.body()
            val locationName = weatherData.getLocation().getName()
            val countryName = weatherData.getLocation().getCountryName()
            val locationTemperature = weatherData.getCurrentInfo().getTemp()
            LocationInfoModel(locationName, countryName, locationTemperature)
        } else {
            println("Ошибка запроса")
            LocationInfoModel("Ошибка", "Ошибка", 0.0)
        }

        return locationInfoModel
    }

    private fun checkResponseStatus(response: HttpResponse): Boolean {
        return response.status.value in 200..299
    }


}