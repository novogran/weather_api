package data.mapper

import data.common.Mapper
import data.dto.WeatherApiServerModel
import domain.entity.WeatherEntity
import javax.inject.Inject

class WeatherEntityMapper @Inject constructor(): Mapper<WeatherApiServerModel, WeatherEntity> {
    override fun map(from: WeatherApiServerModel): WeatherEntity {
        return WeatherEntity(
            from.locationInfoServerModel.name,
            from.locationInfoServerModel.country,
            from.currentInfoServerModel.temp
        )
    }
}
