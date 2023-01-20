package domain.mapper

import common.Mapper
import domain.entity.WeatherEntity
import presentaion.entity.WeatherViewData

class WeatherViewDataMapper : Mapper<WeatherEntity, WeatherViewData> {
    override fun map(from: WeatherEntity): WeatherViewData {
        return WeatherViewData(
            locationName = from.locationName,
            countryName = from.countryName,
            locationTemperature = from.locationTemperature
        )
    }
}