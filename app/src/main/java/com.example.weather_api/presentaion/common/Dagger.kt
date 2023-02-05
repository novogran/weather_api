package com.example.weather_api.presentaion.common

import com.example.weather_api.presentaion.view_model.MainViewModel
import dagger.Binds
import dagger.Component
import dagger.Module
import dagger.Provides
import data.api.WeatherApi
import data.api.WeatherComApi
import data.repo.WeatherRepoImpl
import domain.repo.WeatherRepo
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

@Component(modules = [AppModule::class])
interface AppComponent{
    fun inject(mainViewModel: MainViewModel)
}

@Module(includes = [AppBindModule::class, NetworkModule::class])
class AppModule

@Module
class NetworkModule {
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
}

@Module
interface AppBindModule {
    @Binds
    fun bindWeatherApiImpl_to_WeatherApi(weatherComApi: WeatherComApi): WeatherApi

    @Binds
    fun bindWeatherRepoImpl_to_WeatherRepo(weatherRepoImpl: WeatherRepoImpl): WeatherRepo
}

