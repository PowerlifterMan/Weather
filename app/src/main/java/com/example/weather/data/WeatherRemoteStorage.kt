package com.example.weather.data

import com.example.weather.domain.WeatherData
import com.example.weather.domain.WeatherUseCase
import com.example.weather.presentation.main.DEFAULT_SOURCE_NAME
import com.example.weather.presentation.main.SOURCE_NINJAS
import com.example.weather.presentation.main.SOURCE_OPEN_METEO
import com.example.weather.presentation.main.SOURCE_OPEN_WEATHER
import io.reactivex.rxjava3.core.Single

class WeatherRemoteStorage {

    fun getWeather(
        lat: Float = WeatherUseCase.DEFAULT_LATITUDE,
        lon: Float = WeatherUseCase.DEFAULT_LONGITUDE,
        sourceName: String = DEFAULT_SOURCE_NAME
    ): Single<WeatherData> {
        val currentRepo: WeatherRepository = when (sourceName) {
            SOURCE_OPEN_METEO -> {
                OpenMeteoRepositoryImpl
            }
            SOURCE_OPEN_WEATHER -> {
                OpenWeatheRepositoryImpl
            }
            SOURCE_NINJAS -> {
                NinjasRepositoryImpl
            }
            else -> OpenWeatheRepositoryImpl
        }
        return currentRepo.getWeather(lat = lat, lon = lon)
    }

    fun getCityDto(city: String): Single<List<GeocodingDTO>> {
        return OpenWeatheRepositoryImpl.getCityByName(city)
    }

}