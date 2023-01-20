package common

import dagger.Component
import dagger.Module
import dagger.Provides
import data.api.WeatherApi
import data.api.WeatherComApi
import data.mapper.WeatherEntityMapper
import data.repo.WeatherRepoImpl
import domain.GetWeatherUseCase
import domain.mapper.WeatherViewDataMapper
import domain.repo.WeatherRepo
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import presentaion.WeatherViewModel


@Component(modules = [AppModule::class])
interface AppComponent{
    fun inject(weatherViewModel: WeatherViewModel)
}

@Module
class AppModule {
    @Provides
    fun provideHttpClient(): HttpClient {
        return HttpClient(CIO) {
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                })
            }
        }
    }

    @Provides
    fun provideWeatherApi(weatherComApi: WeatherComApi): WeatherApi {
        return weatherComApi
    }

    @Provides
    fun provideWeatherComApi(
        client: HttpClient
    ): WeatherComApi {
        return WeatherComApi(
            client = client
        )
    }

    @Provides
    fun provideWeatherEntityMapper(): WeatherEntityMapper {
        return WeatherEntityMapper()
    }

    @Provides
    fun provideWeatherRepo(weatherRepoImpl: WeatherRepoImpl): WeatherRepo {
        return weatherRepoImpl
    }

    @Provides
    fun provideWeatherRepoImpl(
        api: WeatherApi, mapper: WeatherEntityMapper
    ): WeatherRepoImpl {
        return WeatherRepoImpl(
            api = api, mapper = mapper
        )
    }

    @Provides
    fun provideAppDispatchers(): AppDispatchers {
        return AppDispatchers()
    }

    @Provides
    fun provideWeatherViewDataMapper(): WeatherViewDataMapper {
        return WeatherViewDataMapper()
    }

    @Provides
    fun provideGetWeatherUseCase(
        weatherRepo: WeatherRepo, weatherViewDataMapper: WeatherViewDataMapper, dispatcher: AppDispatchers
    ): GetWeatherUseCase {
        return GetWeatherUseCase(
            weatherRepo = weatherRepo, commonItemMapper = weatherViewDataMapper, dispatcher = dispatcher
        )
    }

}
