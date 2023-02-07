package com.example.weather_api.presentation.entity

data class WeatherViewData(
    val locationName: String,
    val countryName: String,
    val locationTemperature: Double
)