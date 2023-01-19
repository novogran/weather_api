import presentaion.WeatherViewModel

private val weatherFinder = WeatherViewModel()

suspend fun main() {
    weatherFinder.findWeather()
}