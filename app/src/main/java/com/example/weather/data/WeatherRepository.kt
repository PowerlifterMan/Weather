package com.example.weather.data

import com.example.weather.domain.WeatherData
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

interface WeatherRepository {

    fun getWeather(lat: Float, lon: Float, cityName: String, cityKladr: String): Single<WeatherData>
    fun getCityByName(cityName: String):Single<List<GeocodingDTO>>
}