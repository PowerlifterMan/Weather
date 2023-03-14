package com.example.weather.ninjas

import com.example.weather.data.OpenWeatherApi
import com.example.weather.retrofit.OpenWeatherRetrofitClient

object NinjasCommon {

    private val BASE_URL_OPEN = "https://api.api-ninjas.com/"
    val retrofitService: NinjasApi
        get() = NinjasRetrofitClient.getClient(BASE_URL_OPEN).create(NinjasApi::class.java)
}