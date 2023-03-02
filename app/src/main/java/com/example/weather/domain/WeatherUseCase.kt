package com.example.weather.domain

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.weather.data.Mappers
import com.example.weather.retrofit.OpenWeatherDto
import com.example.weather.retrofit.openWeather.City
import com.example.weather.retrofit.openWeather.GeocodingDTO
import com.example.weather.retrofit.openWeather.OpenWeatherForecastDTO
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

class WeatherUseCase(private val weatherRepository: OpenWeatherRepository) {

    fun getForecastOpenWeather(
        lat: String = DEFAULT_LATITUDE,
        lon: String = DEFAULT_LONGITUDE
    ): Single<OpenWeatherForecastDTO> {
        return weatherRepository.getForecastOpenWeather(lat = lat, lon = lon)
    }

    fun getOpenWeatherFOrecastData(
        lat: String = DEFAULT_LATITUDE,
        lon: String = DEFAULT_LONGITUDE
    ): Single<OpenWeatherForecastDTO> {
        return weatherRepository.getForecastOpenWeather(lat = lat, lon = lon)
    }

//    fun getOpenWeatherCity(city)

    fun getWeatherOpenWeather(
        lat: String = DEFAULT_LATITUDE,
        lon: String = DEFAULT_LONGITUDE
    ): Single<OpenWeatherDto> {
        return weatherRepository.getWeatherOpenWeather(lat = lat, lon = lon)
    }
    fun getCityDto(city: String):Single<List<GeocodingDTO>> {
        return weatherRepository.getCityByName(city)
    }

    companion object {
        const val DEFAULT_LATITUDE = "44.044"
        const val DEFAULT_LONGITUDE = "42.86"
    }
}