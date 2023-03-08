package com.example.weather.OpenMeteo

import com.example.weather.data.OpenWeatherApi
import com.example.weather.retrofit.OpenWeatherRetrofitClient

object OpenMeteoCommon {

    private val BASE_URL_OPEN = "https://api.open-meteo.com/"
    val retrofitService: OpenMeteoApi
        get() = OpenMeteoRetrofitClient.getClient(BASE_URL_OPEN).create(OpenMeteoApi::class.java)
}