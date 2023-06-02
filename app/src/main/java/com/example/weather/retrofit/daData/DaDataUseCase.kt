package com.example.weather.retrofit.daData

import io.reactivex.rxjava3.core.Single

class DaDataUseCase(private val daDataRepository: DaDataRepository) {

    suspend fun getCityDto(name: String): Suggestions
    {
        return daDataRepository.getCity(name)
    }

}