package data

import Mapper
import kotlinx.serialization.SerialName

@kotlinx.serialization.Serializable
data class WeatherApiServerModel(
    @SerialName("location")
    val locationInfoServerModel: WeatherApiLocationInfoServerModel,
    @SerialName("current")
    val currentInfoServerModel: WeatherApiCurrentInfoServerModel
) : Mapper<CommonDataModel> {
    override fun to() = CommonDataModel(
        locationInfoServerModel.name,
        locationInfoServerModel.country, currentInfoServerModel.temp
    )
}