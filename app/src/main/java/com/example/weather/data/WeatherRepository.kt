package com.example.weather.data

import com.example.weather.domain.WeatherData
import com.example.weather.retrofit.openWeather.GeocodingDTO
import io.reactivex.rxjava3.core.Single

interface WeatherRepository {
    fun getWeather(lat: Float, lon: Float): Single<WeatherData>
    fun getCityByName(cityName: String):Single<List<GeocodingDTO>>
}