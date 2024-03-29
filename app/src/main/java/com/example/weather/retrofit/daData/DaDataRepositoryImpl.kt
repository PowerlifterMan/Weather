package com.example.weather.retrofit.daData

import android.util.Log
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
        Log.e("dadata",jsonObject.toString() )
        val bodyRequest = jsonObject.toString()
            .toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())
        val data = myService.getAddrdessesList(
            contentType = "application/json",
            token = "9e01e829bc289bb130dbf457fce0d371f44d487f",
            secretToken = "ead3ad17504756b283a5439bc2f0f20c27eadbcb",
            query = bodyRequest
        ).subscribeOn(Schedulers.io())
        return data
    }
}