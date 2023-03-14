package com.example.weather.ninjas

import io.reactivex.rxjava3.core.Single

interface NinjasRepository {
    fun getWeather(lon: Float, lat: Float): Single<NinjasDTO>
}