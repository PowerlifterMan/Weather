package com.example.weather.OpenMeteo

import com.example.weather.data.OpenMeteoRepository
import io.reactivex.rxjava3.core.Single


class OpenMeteoUseCase(private val openMeteoRepository: OpenMeteoRepository) {

    fun getForecastOpenMeteo(latitude: Float, longitude: Float): Single<OpenMeteoDTO> {
        return openMeteoRepository.getWeatherOpenMeteo(lat = latitude, lon = longitude)
    }
}