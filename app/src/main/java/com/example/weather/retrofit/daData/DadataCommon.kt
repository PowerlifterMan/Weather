package com.example.weather.retrofit.daData

import com.example.weather.retrofit.OpenWeatherRetrofitClient

object DadataCommon {
//  private val BASE_URL = "https://suggestions.dadata.ru/suggestions/api/4_1/rs/"
    private val BASE_URL = "https://suggestions.dadata.ru/suggestions/api/4_1/rs/"

    val retrofitService: DadataRetrofitServices
        get() = DaDataRetrofitClient.getClient(BASE_URL).create(DadataRetrofitServices::class.java)
}