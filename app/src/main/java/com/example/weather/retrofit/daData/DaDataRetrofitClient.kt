package com.example.weather.retrofit.daData

import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object DaDataRetrofitClient {
        private var retrofit: Retrofit? = null

        fun getClient(baseUrl: String): Retrofit {
            if (retrofit == null) {
                retrofit = Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                    ////  не забыть  okhttp client
            //        .client("")
                    .build()
            }
            return retrofit!!
        }
    }