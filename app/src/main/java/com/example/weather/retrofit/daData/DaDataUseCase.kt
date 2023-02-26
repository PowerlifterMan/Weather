package com.example.weather.retrofit.daData

import com.example.weather.domain.OpenWeatherRepository
import io.reactivex.rxjava3.core.Single

class DaDataUseCase(private val daDataRepository: DaDataRepository) {

    fun getCityDto(name: String): Single<Suggestions>
    {
        return daDataRepository.getCity(name)
    }

}