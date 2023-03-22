package com.example.weather.data

import com.example.weather.domain.WeatherData
import io.reactivex.rxjava3.core.Single

interface WeatherRepository {
    fun getWeatherFromRemote(lat: Float, lon: Float): Single<WeatherData>
    fun getCityByName(cityName: String):Single<List<GeocodingDTO>>
    fun getWeatherFromLocal():Single<WeatherData>
    fun saveWeatherToLocal(weatherData: WeatherData)
    fun checkNeedToUpdate():
}