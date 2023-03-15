package com.example.weather.domain

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.weather.OpenMeteo.OpenMeteoRepository
import com.example.weather.OpenMeteo.OpenMeteoRepositoryImpl
import com.example.weather.data.Mappers
import com.example.weather.data.OpenWeatheRepositoryImpl
import com.example.weather.ninjas.NinjasRepositoryImpl
import com.example.weather.presentation.main.DEFAULT_SOURCE_NAME
import com.example.weather.presentation.main.SOURCE_NINJAS
import com.example.weather.presentation.main.SOURCE_OPEN_METEO
import com.example.weather.presentation.main.SOURCE_OPEN_WEATHER
import com.example.weather.retrofit.OpenWeatherDto
import com.example.weather.retrofit.openWeather.City
import com.example.weather.retrofit.openWeather.GeocodingDTO
import com.example.weather.retrofit.openWeather.OpenWeatherForecastDTO
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

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
        val returned = currentRepo.getWeather(lat = lat.toString(), lon = lon.toString())
        return returned

    }

    fun getCityByName(city: String = "Moscow") {
        val currentRepo = SOURCE_OPEN_WEATHER
        val returned = currentRepo.getCityByName(cityName = city)
    }

    fun getForecastOpenWeather(
        lat: Float = DEFAULT_LATITUDE,
        lon: Float = DEFAULT_LONGITUDE
    ): Single<OpenWeatherForecastDTO> {

        return weatherRepository.getForecastOpenWeather(lat = lat.toString(), lon = lon.toString())
    }

    fun getOpenWeatherFOrecastData(
        lat: Float = DEFAULT_LATITUDE,
        lon: Float = DEFAULT_LONGITUDE
    ): Single<OpenWeatherForecastDTO> {
        return weatherRepository.getForecastOpenWeather(lat = lat.toString(), lon = lon.toString())
    }

//    fun getOpenWeatherCity(city)

    fun getWeatherOpenWeather(
        lat: Float = DEFAULT_LATITUDE,
        lon: Float = DEFAULT_LONGITUDE
    ): Single<OpenWeatherDto> {
        return weatherRepository.getWeatherOpenWeather(lat = lat.toString(), lon = lon.toString())
    }

    fun getCityDto(city: String): Single<List<GeocodingDTO>> {
        return weatherRepository.getCityByName(city)
    }

    companion object {
        const val DEFAULT_LATITUDE = 44.044f
        const val DEFAULT_LONGITUDE = 42.86f
        const val DEFAULT_CITY = "Yessentuki"

    }
}