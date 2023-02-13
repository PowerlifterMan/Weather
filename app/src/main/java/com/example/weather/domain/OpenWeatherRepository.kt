package com.example.weather.domain

import androidx.lifecycle.LiveData
import com.example.weather.retrofit.OpenWeatherDto
import com.example.weather.retrofit.openWeather.OpenWeatherForecastDTO

interface OpenWeatherRepository {

    fun getForecastOpenWeather(lat: String, lon: String): LiveData<OpenWeatherForecastDTO>

    fun getWeatherOpenWeather(lat: String, lon: String): LiveData<OpenWeatherDto>

}