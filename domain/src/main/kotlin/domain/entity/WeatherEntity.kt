package domain.entity

data class WeatherEntity(
    val locationName: String,
    val countryName: String,
    val locationTemperature: Double
)