package com.example.weather.retrofit

import android.telecom.Call
import com.example.weather.domain.DayData
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header

interface RetrofitServices {
    @GET("forecast")
    fun getForecasrList(
        @Header("Content-Type") contentType: String,
        @Header("Authorization") token: String,
        @Body query: RequestBody
    ): retrofit2.Call<Suggestions>
}
