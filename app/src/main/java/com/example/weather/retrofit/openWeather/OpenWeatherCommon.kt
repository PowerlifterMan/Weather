package com.example.weather.retrofit.openWeather

import com.example.weather.data.OpenWeatherApi
import com.example.weather.retrofit.RetrofitClient


object OpenWeatherCommon {
    private val BASE_URL_GISMETEO = "https://api.gismeteo.net/v2/weather/"
    private val BASE_URL_OPEN = "https://api.openweathermap.org"
    val retrofitService: OpenWeatherApi
        get() = RetrofitClient.getClient(BASE_URL_OPEN).create(OpenWeatherApi::class.java)
}
