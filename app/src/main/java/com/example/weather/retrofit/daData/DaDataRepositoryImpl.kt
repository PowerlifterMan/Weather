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
        val jsonObject = JsonObject()
        val jsonObjectBoundCity = JsonObject()
        jsonObjectBoundCity.addProperty("value", "city")
        val jsonObjectBoundSettlement = JsonObject()
        jsonObjectBoundSettlement.addProperty("value", "settlement")
        jsonObject.addProperty("query", "$name")
        jsonObject.add("from_bound", jsonObjectBoundCity)
        jsonObject.add("to_bound", jsonObjectBoundSettlement)
        val bodyRequest = jsonObject.toString()
            .toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())
        val data = myService.getAddrdessesList(
            contentType = "application/json",
            accept = "application/json",
            token = "Token 9e01e829bc289bb130dbf457fce0d371f44d487f",
            query = bodyRequest
        )
        return data
    }
}