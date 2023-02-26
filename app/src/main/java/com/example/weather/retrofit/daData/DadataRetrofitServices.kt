package com.example.weather.retrofit.daData

import io.reactivex.rxjava3.core.Single
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface DadataRetrofitServices {
    @POST("suggest/address")
    fun getAddrdessesList(
        @Header("Content-Type") contentType: String,
        @Header("Authorization") token: String,
        @Body query: RequestBody
    ): Single<Suggestions>

}
