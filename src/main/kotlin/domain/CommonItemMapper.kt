package domain

import Mapper
import domain.entity.WeatherEntity

class CommonItemMapper : Mapper<WeatherEntity, CommonItem> {
    override fun map(from: WeatherEntity): CommonItem {
        return CommonItem.Success(from.locationName, from.countryName, from.locationTemperature)
    }
}