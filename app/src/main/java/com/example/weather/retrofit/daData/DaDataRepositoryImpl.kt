package com.example.weather.retrofit.daData

import com.google.gson.JsonObject
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody

object DaDataRepositoryImpl : DaDataRepository {
    val myService = DadataCommon.retrofitService

    override fun getCity(name: String): Single<Suggestions> {
        val jsonObject = JsonObject().addProperty("query", name)
        val bodyRequest = jsonObject.toString()
            .toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())
        val data = myService.getAddrdessesList(
            "application/json",
            "d2db856b9ee6c152a68284caf45b2ed1924f6fe9",
            bodyRequest
        ).subscribeOn(Schedulers.io())
        return data
    }
}