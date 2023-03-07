package com.example.weather.OpenMeteo

import io.reactivex.rxjava3.core.Single

interface OpenMeteoRepository {
    fun getWeatherOpenMeteo(lat: Float, lon: Float): Single<OpenMeteoDTO>
}