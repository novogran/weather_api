package presentaion.mapper

import common.Mapper
import domain.entity.WeatherEntity
import presentaion.entity.WeatherViewData
import javax.inject.Inject

class WeatherViewDataMapper @Inject constructor() : Mapper<WeatherEntity, WeatherViewData> {
    override fun map(from: WeatherEntity): WeatherViewData {
        return WeatherViewData(
            locationName = from.locationName,
            countryName = from.countryName,
            locationTemperature = from.locationTemperature
        )
    }
}