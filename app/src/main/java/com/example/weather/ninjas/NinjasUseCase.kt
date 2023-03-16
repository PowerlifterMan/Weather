package com.example.weather.ninjas

import com.example.weather.data.NinjasDTO
import com.example.weather.data.NinjasRepository
import io.reactivex.rxjava3.core.Single

class NinjasUseCase(private val repository: NinjasRepository) {
    fun getWeather(latitude: Float, longitude: Float): Single<NinjasDTO> {
        return repository.getWeather(lon = longitude, lat = latitude)
    }
}