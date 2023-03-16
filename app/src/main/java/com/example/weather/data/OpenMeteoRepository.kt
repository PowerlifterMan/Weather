package com.example.weather.data

import com.example.weather.OpenMeteo.OpenMeteoDTO
import io.reactivex.rxjava3.core.Single

interface OpenMeteoRepository {
    fun getWeatherOpenMeteo(lat: Float, lon: Float): Single<OpenMeteoDTO>
}