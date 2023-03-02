package com.example.weather.domain

import androidx.lifecycle.LiveData
import com.example.weather.retrofit.OpenWeatherDto
import com.example.weather.retrofit.openWeather.GeocodingDTO
import com.example.weather.retrofit.openWeather.OpenWeatherForecastDTO
import io.reactivex.rxjava3.core.Single

interface OpenWeatherRepository {

    fun getForecastOpenWeather(lat: String, lon: String): Single<OpenWeatherForecastDTO>

    fun getWeatherOpenWeather(lat: String, lon: String): Single<OpenWeatherDto>

    fun getCityByName(cityName: String):Single<List<GeocodingDTO>>

}