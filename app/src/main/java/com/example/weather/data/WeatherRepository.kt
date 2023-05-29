package com.example.weather.data

import androidx.lifecycle.LiveData
import com.example.weather.domain.WeatherData
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

interface WeatherRepository {

    suspend fun getWeather(lat: Float, lon: Float, cityName: String, cityKladr: String): WeatherData
    suspend fun getCityByName(cityName: String):List<GeocodingDTO>
}