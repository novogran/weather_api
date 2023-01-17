package data

import domain.GetWeatherData
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*

class GetWeatherDataImpl(private val client: HttpClient) : GetWeatherData {

    companion object {
        private const val HOST_URL = "api.weatherapi.com"
        private const val PATH = "v1/current.json"
        private const val API_KEY = "69e6d334e8c5405fbe4132858231001"
    }

    override suspend fun getWeather(weatherLocationToSearch: String?): CommonDataModel {

        val response: HttpResponse = client.get {
            url {
                protocol = URLProtocol.HTTP
                host = HOST_URL
                path(PATH)
                parameter("key", API_KEY)
                parameter("q", weatherLocationToSearch)
            }
        }
        return response.body<WeatherApiServerModel>().to()
    }
}