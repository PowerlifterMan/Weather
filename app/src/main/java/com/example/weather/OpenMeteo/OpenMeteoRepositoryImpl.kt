package com.example.weather.OpenMeteo

import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

object OpenMeteoRepositoryImpl : OpenMeteoRepository {

    val myService = OpenMeteoCommon.retrofitService

    override fun getWeatherOpenMeteo(lat: Float, lon: Float): Single<OpenMeteoDTO> {
        return myService.getOpenMeteoForecast(
            latitude = lat,
            longitude = lon,
            daily = "temperature_2m_max",
        ).subscribeOn(Schedulers.io())
    }
}