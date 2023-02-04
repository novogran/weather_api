package com.example.weather_api.presentation.model

sealed interface WeatherViewState {

    data class Success(
        val locationName: String = "",
        val countryName: String = "",
        val locationTemperature: Double = 0.0
    ) : WeatherViewState

    data class Failed(val failureText: String) : WeatherViewState {
        override fun equals(other: Any?): Boolean = false

        override fun hashCode(): Int = failureText.hashCode()
    }


}