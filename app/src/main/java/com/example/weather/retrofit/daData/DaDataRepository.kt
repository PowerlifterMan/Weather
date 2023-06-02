package com.example.weather.retrofit.daData

import io.reactivex.rxjava3.core.Single

interface DaDataRepository {
    suspend fun getCity(name: String): Suggestions
}