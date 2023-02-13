package com.example.weather.domain

import androidx.lifecycle.LiveData
import com.example.weather.retrofit.OpenWeatherDto
import com.example.weather.retrofit.openWeather.OpenWeatherForecastDTO

class WeatherUseCase(private val weatherRepository: OpenWeatherRepository) {

    fun getForecastOpenWeather(lat: String = DEFAULT_LATITUDE, lon: String = DEFAULT_LONGITUDE): LiveData<OpenWeatherForecastDTO> {
        return weatherRepository.getForecastOpenWeather(lat = lat, lon = lon)
    }

    fun getWeatherOpenWeather(lat: String, lon: String): LiveData<OpenWeatherDto> {
        return weatherRepository.getWeatherOpenWeather(lat = lat, lon = lon)
    }

    companion object {
        const val DEFAULT_LATITUDE = "44.044"
        const val DEFAULT_LONGITUDE = "42.86"
    }
}