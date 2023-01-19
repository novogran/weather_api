package presentaion.model

sealed interface WeatherViewState {

    data class Success(
        val locationName: String,
        val countryName: String,
        val locationTemperature: Double
    ) : WeatherViewState

    data class Failed(val failureText: String) : WeatherViewState
}