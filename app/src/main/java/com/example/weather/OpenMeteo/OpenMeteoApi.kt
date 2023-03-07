package com.example.weather.OpenMeteo

import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenMeteoApi {
    @GET("forecast")
        fun getOpenMeteoForecast(
        @Query("latitude") latitude: Float?,
        @Query("longitude") longitude: Float?,
        @Query("daily") daily: String?,
        ): Single<OpenMeteoDTO>
}