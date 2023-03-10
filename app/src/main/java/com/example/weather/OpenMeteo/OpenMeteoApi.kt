package com.example.weather.OpenMeteo

import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenMeteoApi {
    @GET("v1/forecast")
    fun getOpenMeteoForecast(
        @Query("latitude") latitude: Float?,
        @Query("longitude") longitude: Float?,
        @Query("daily") daily: String? = null,
        @Query("hourly") hourly: String? = null,
        @Query("timeformat") timeformat: String? =null,
        @Query("current_weather") current_weather: Boolean? = null,
        @Query("timezone") timezone: String?
    ): Single<OpenMeteoDTO>
}