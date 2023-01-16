package presentaion

private val weatherFinder = WeatherFinder()

suspend fun main() {
    weatherFinder.findWeather()
}