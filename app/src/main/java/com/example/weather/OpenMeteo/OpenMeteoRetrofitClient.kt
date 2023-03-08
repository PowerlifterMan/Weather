package com.example.weather.OpenMeteo

import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object OpenMeteoRetrofitClient {
    private var retrofit: Retrofit? = null

    fun getClient(baseUrl: String): Retrofit {
        val interceptor = HttpLoggingInterceptor(
            HttpLoggingInterceptor.Logger {
                it.chunked(2048).forEach { message ->
                    Log.d("OkHttp", message)
                }
            },
        )
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        val client = OkHttpClient.Builder().apply {
            this.addInterceptor(interceptor)
        }.build()
        if (retrofit == null) {
            retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .client(client)
                .build()
        }
        return retrofit!!
    }

}
//val logging = HttpLoggingInterceptor(
//    HttpLoggingInterceptor.Logger {
//        it.chunked(2048).forEach { message ->
//            logDebug("OkHttp", message)
//        }
//    },
//)
//logging.level = HttpLoggingInterceptor.Level.BODY
