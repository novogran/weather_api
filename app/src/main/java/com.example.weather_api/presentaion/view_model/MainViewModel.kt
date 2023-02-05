package com.example.weather_api.presentaion.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weather_api.presentaion.common.DaggerAppComponent
import com.example.weather_api.presentaion.common.toErrorMessage
import domain.GetWeatherUseCaseImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import presentaion.mapper.WeatherViewDataMapper
import com.example.weather_api.presentaion.model.WeatherViewState
import javax.inject.Inject

class MainViewModel : ViewModel() {

    val appComponent = DaggerAppComponent.create()

    @Inject
    lateinit var getWeatherUseCase: GetWeatherUseCaseImpl

    private var weatherMutableStateFlow =
        MutableStateFlow<WeatherViewState>(WeatherViewState.Success())

    val weatherStateFlow: StateFlow<WeatherViewState> =
        weatherMutableStateFlow.asStateFlow()

    fun load(weatherLocationToSearch: String) {
        appComponent.inject(this)
        viewModelScope.launch(Dispatchers.IO) {
            weatherMutableStateFlow.value = try {
                val weatherEntity = getWeatherUseCase.execute(weatherLocationToSearch)
                val weatherData = WeatherViewDataMapper().map(weatherEntity)
                WeatherViewState.Success(
                    weatherData.locationName,
                    weatherData.countryName,
                    weatherData.locationTemperature
                )
            } catch (e: Exception) {
                WeatherViewState.Failed(e.toErrorMessage())
            }

        }
    }
}