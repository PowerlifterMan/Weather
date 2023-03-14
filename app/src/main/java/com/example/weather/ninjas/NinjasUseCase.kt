package com.example.weather.ninjas

import io.reactivex.rxjava3.core.Single

class NinjasUseCase(private val repository: NinjasRepository) {
    fun getWeather(latitude: Float, longitude: Float): Single<NinjasDTO> {
        return repository.getWeather(lon = longitude, lat = latitude)
    }
}