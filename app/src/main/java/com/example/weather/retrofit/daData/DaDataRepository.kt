package com.example.weather.retrofit.daData

import io.reactivex.rxjava3.core.Single

interface DaDataRepository {
    fun getCity(name: String) :Single<Suggestions>
}