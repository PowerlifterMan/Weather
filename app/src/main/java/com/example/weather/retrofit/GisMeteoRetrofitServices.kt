package com.example.weather.retrofit

import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header

interface GisMeteoRetrofitServices {
    @GET("forecast")
    fun getForecasrList(
        @Header("X-Gismeteo-Token") token: String,
        @Header("Accept-Encoding") encoding: String,
        @Body query: RequestBody
    ): retrofit2.Call<Suggestions>
}
