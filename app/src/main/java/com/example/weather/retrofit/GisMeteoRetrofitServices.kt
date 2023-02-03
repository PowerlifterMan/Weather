package com.example.weather.retrofit

import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface GisMeteoRetrofitServices {
    @GET("v2/weather/current/{geoId}")
    fun getForecasrList(
        @Header("X-Gismeteo-Token") token: String,
        @Header("Accept-Encoding") encoding: String,
        @Path("geoId") geoId: String?,
        @Query("lang") lang: String?,
    ): retrofit2.Call<CurrentWeatherDto>
}
