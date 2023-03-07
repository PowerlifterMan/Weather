package com.example.weather.OpenMeteo

import io.reactivex.rxjava3.core.Single


class OpenMeteoUseCase(private val openMeteoRepository: OpenMeteoRepository) {

    fun getForecastOpenMeteo(latitude: Float, longitude: Float): Single<OpenMeteoDTO> {
        return openMeteoRepository.getWeatherOpenMeteo(lat = latitude, lon = longitude)
    }
}