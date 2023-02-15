package com.example.weather.retrofit.gisMeteo

import com.example.weather.retrofit.RetrofitClient

object GismeteoCommon {
    private val BASE_URL_GISMETEO = "https://api.gismeteo.net/v2/weather/"
    val retrofitService: GisMeteoRetrofitServices
        get() = RetrofitClient.getClient(BASE_URL_GISMETEO).create(GisMeteoRetrofitServices::class.java)
}

