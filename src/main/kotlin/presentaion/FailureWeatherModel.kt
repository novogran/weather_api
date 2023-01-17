package presentaion

import domain.CommonWeatherModel

class FailureWeatherModel(private val text: String) : CommonWeatherModel() {
    override fun text() = text
}