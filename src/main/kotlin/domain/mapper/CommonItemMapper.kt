package domain.mapper

import Mapper
import presentaion.model.CommonItem
import domain.FailureFactory
import domain.entity.WeatherEntity

class CommonItemMapper : Mapper<WeatherEntity, CommonItem> {
    override fun map(from: WeatherEntity): CommonItem {
        return try {
            CommonItem.Success(from.locationName, from.countryName, from.locationTemperature)
        } catch (e: Exception) {
            CommonItem.Failed(FailureFactory().handle(e))
        }
    }
}