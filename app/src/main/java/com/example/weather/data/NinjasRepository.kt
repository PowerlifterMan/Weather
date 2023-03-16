package com.example.weather.data

import com.example.weather.data.dto.NinjasDTO
import io.reactivex.rxjava3.core.Single

interface NinjasRepository {
    fun getWeather(lon: Float, lat: Float): Single<NinjasDTO>
}