package presentaion.model

sealed class CommonItem {

    data class Success(
        val locationName: String,
        val countryName: String,
        val locationTemperature: Double
    ) : CommonItem()

    data class Failed(val failureText: String) : CommonItem() {
    }
}