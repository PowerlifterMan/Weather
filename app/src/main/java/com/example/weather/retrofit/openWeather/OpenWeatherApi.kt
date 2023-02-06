package com.example.weather.retrofit.openWeather

import com.example.weather.retrofit.OpenWeatherDto
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenWeatherApi {
    @GET("data/2.5/weather")
    fun getForecast(
        @Query("lat") latitude: String?,
        @Query("lon") longitude: String?,
        @Query("units") units: String?,
        @Query("lang") lang: String?,
        @Query("cnt") nDays: Int?,
        @Query("appid") appId: String?,
    ): retrofit2.Call<OpenWeatherDto>

    @GET("data/2.5/weather")
    fun getForecastByCityName(
        @Query("q") cityName: String?,
        @Query("units") units: String?,
        @Query("lang") lang: String?,
        @Query("cnt") nDays: Int?,
        @Query("appid") appId: String?,
    ): retrofit2.Call<OpenWeatherDto>

}
