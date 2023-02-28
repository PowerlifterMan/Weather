package com.example.weather.data

import com.example.weather.retrofit.OpenWeatherDto
import com.example.weather.retrofit.openWeather.GeocodingDTO
import com.example.weather.retrofit.openWeather.OpenWeatherForecastDTO
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenWeatherApi {
    @GET("data/2.5/weather")
    fun getWeatherByCoorddinates(
        @Query("lat") latitude: String?,
        @Query("lon") longitude: String?,
        @Query("units") units: String?,
        @Query("lang") lang: String?,
        @Query("appid") appId: String?,
    ): Single<OpenWeatherDto>

    @GET("data/2.5/forecast")
    fun getForecastByCoorddinates(
        @Query("lat") latitude: String?,
        @Query("lon") longitude: String?,
        @Query("units") units: String?,
        @Query("lang") lang: String?,
        @Query("appid") appId: String?,
        @Query("cnt") nDays: Int
    ): Single<OpenWeatherForecastDTO>

    @GET("data/2.5/forecast")
    fun getForecastByCoorddinates(
        @Query("lat") latitude: String?,
        @Query("lon") longitude: String?,
        @Query("units") units: String?,
        @Query("lang") lang: String?,
        @Query("appid") appId: String?
    ): Single<OpenWeatherForecastDTO>

    @GET("geo/1.0/direct?q")
    fun getCoordByName(
        @Query("q") cityName: String?,
        @Query("appid") appId: String?
    ):Single<GeocodingDTO>

}
