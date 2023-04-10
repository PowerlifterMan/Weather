package com.example.weather.ninjas

import com.example.weather.data.dto.NinjasDTO
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface NinjasApi {
//    @POST("suggest/address")
    @GET("v1/weather")
    fun getCurrentWeather(
        @Header("X-Api-Key") apiKey: String,
        @Query("lat") latitude: String,
        @Query("lon") longitude : String,
    ): Single<NinjasDTO>

}
