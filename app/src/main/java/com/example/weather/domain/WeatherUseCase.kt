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
    fun getCityDto(city: String):Single<List<GeocodingDTO>> {
        return weatherRepository.getCityByName(city)
    }

    companion object {
        const val DEFAULT_LATITUDE = 44.044f
        const val DEFAULT_LONGITUDE = 42.86f
    }
}