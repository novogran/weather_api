package data.mapper

import Mapper
import data.dto.WeatherApiServerModel
import domain.entity.WeatherEntity

class WeatherEntityMapper : Mapper<WeatherApiServerModel, WeatherEntity> {
    override fun map(from: WeatherApiServerModel): WeatherEntity {
        return WeatherEntity(
            from.locationInfoServerModel.name,
            from.locationInfoServerModel.country,
            from.currentInfoServerModel.temp
        )
    }
}
