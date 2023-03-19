package com.example.weather.domain

import com.example.weather.data.*
import com.example.weather.presentation.main.DEFAULT_SOURCE_NAME
import com.example.weather.presentation.main.SOURCE_NINJAS
import com.example.weather.presentation.main.SOURCE_OPEN_METEO
import com.example.weather.presentation.main.SOURCE_OPEN_WEATHER
import io.reactivex.rxjava3.core.Single


class WeatherUseCase() {


    fun getWeather(
        lat: Float = DEFAULT_LATITUDE,
        lon: Float = DEFAULT_LONGITUDE,
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

//    fun getCityByName(city: String = "Moscow") {
//        val currentRepo = SOURCE_OPEN_WEATHER
//        val returned = currentRepo.getCityByName(cityName = city)
//    }
//
//    fun getForecastOpenWeather(
//        lat: Float = DEFAULT_LATITUDE,
//        lon: Float = DEFAULT_LONGITUDE
//    ): Single<OpenWeatherForecastDTO> {
//
//        return weatherRepository.getForecastOpenWeather(lat = lat.toString(), lon = lon.toString())
//    }
//
//    fun getOpenWeatherFOrecastData(
//        lat: Float = DEFAULT_LATITUDE,
//        lon: Float = DEFAULT_LONGITUDE
//    ): Single<OpenWeatherForecastDTO> {
//        return weatherRepository.getForecastOpenWeather(lat = lat.toString(), lon = lon.toString())
//    }
//
////    fun getOpenWeatherCity(city)
//
//    fun getWeatherOpenWeather(
//        lat: Float = DEFAULT_LATITUDE,
//        lon: Float = DEFAULT_LONGITUDE
//    ): Single<OpenWeatherDto> {
//        return weatherRepository.getWeatherOpenWeather(lat = lat.toString(), lon = lon.toString())
//    }
//
    fun getCityDto(city: String): Single<List<GeocodingDTO>> {

        return OpenWeatheRepositoryImpl.getCityByName(city)
    }

    companion object {
        const val DEFAULT_LONGITUDE = 42.86f
        const val DEFAULT_CITY = "Yessentuki"
        const val DEFAULT_LATITUDE = 44.044f

    }
}

