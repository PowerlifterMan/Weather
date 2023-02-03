package com.example.weather.retrofit

import retrofit2.http.GET
import retrofit2.http.Query

interface OpenWeatherApi {
    @GET("data/2.5/weather")
    fun getForecast(
        @Query("lat") latitude: String?,
        @Query("lon") longitude: String?,
        @Query("appid") appId: String?,
    ): retrofit2.Call<OpenWeatherDto>
}
