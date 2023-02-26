package com.example.weather.retrofit.gisMeteo

import com.example.weather.retrofit.OpenWeatherRetrofitClient

object GismeteoCommon {
    private val BASE_URL_GISMETEO = "https://api.gismeteo.net/v2/weather/"
    val retrofitService: GisMeteoRetrofitServices
        get() = OpenWeatherRetrofitClient.getClient(BASE_URL_GISMETEO).create(GisMeteoRetrofitServices::class.java)
}

