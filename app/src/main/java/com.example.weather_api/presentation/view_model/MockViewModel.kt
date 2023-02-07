package com.example.weather_api.presentation.view_model

import com.example.weather_api.presentation.model.WeatherViewState


class MockViewModel(
    private val weatherViewState: WeatherViewState.Success
): BaseViewModel {
    override fun load(weatherLocationToSearch: String) {
        weatherViewState.locationName
        weatherViewState.countryName
        weatherViewState.locationTemperature
    }
}
