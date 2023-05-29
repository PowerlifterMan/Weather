package com.example.weather.data

import androidx.lifecycle.LiveData
import com.example.weather.retrofit.OpenWeatherDto
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
    ): OpenWeatherDto

    @GET("data/2.5/forecast")
    fun getForecastByCoorddinates(
        @Query("lat") latitude: String?,
        @Query("lon") longitude: String?,
        @Query("units") units: String?,
        @Query("lang") lang: String?,
        @Query("appid") appId: String?,
        @Query("cnt") nDays: Int
    ): OpenWeatherForecastDTO

    @GET("data/2.5/forecast")
    suspend fun getForecastByCoorddinates(
        @Query("lat") latitude: String?,
        @Query("lon") longitude: String?,
        @Query("units") units: String?,
        @Query("lang") lang: String?,
        @Query("appid") appId: String?
    ): OpenWeatherForecastDTO

    @GET("geo/1.0/direct")
    fun getCoordByName(
        @Query("q") cityName: String?,
        @Query("appid") appId: String?
    ):List<GeocodingDTO>

}
