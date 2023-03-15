package com.example.weather.domain

import com.example.weather.retrofit.OpenWeatherDto
import com.example.weather.retrofit.openWeather.GeocodingDTO
import io.reactivex.rxjava3.core.Single

interface WeatherRepository {
    fun getWeather(lat: String, lon: String): Single<WeatherData>
    fun getCityByName(cityName: String):Single<List<GeocodingDTO>>
}