package com.example.weather.retrofit


object GisMeteoCommon {
    private val BASE_URL = "https://api.gismeteo.net/v2/weather/"
    val retrofitService: GisMeteoRetrofitServices
        get() = RetrofitClient.getClient(BASE_URL).create(GisMeteoRetrofitServices::class.java)
}
