package com.example.weather.ninjas

import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

object NinjasRepositoryImpl : NinjasRepository {
    val service = NinjasCommon.retrofitService
    override fun getWeather(lon: Float, lat: Float): Single<NinjasDTO> {
        return service.getCurrentWeather(
            apiKey = "WqthQnLS3J9U8msOMh/iFw==7ZsAWxaBsOpJ9aaf",
            longitude = lon.toString(),
            latitude = lat.toString()
        ).subscribeOn(Schedulers.io())
    }
}