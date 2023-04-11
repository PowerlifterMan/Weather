package com.example.weather.di

import com.example.weather.data.NinjasRepositoryImpl
import com.example.weather.data.OpenMeteoRepositoryImpl
import com.example.weather.data.OpenWeatheRepositoryImpl
import com.example.weather.data.WeatherRepository
import com.example.weather.domain.WeatherUseCase
import dagger.Binds
import dagger.Module
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
abstract class WeatherModule {
    @Singleton
    @Binds
    abstract fun bindWeatherUseCase(impl: WeatherUseCase): WeatherUseCase

    @Singleton
    @Binds
    @NinjaRepo
    abstract fun bindNinjaRepo(impl: NinjasRepositoryImpl): WeatherRepository

    @Singleton
    @Binds
    @OpenMeteoRepo
    abstract fun bindOpenMeteoRepo(impl: OpenMeteoRepositoryImpl): WeatherRepository

    @Singleton
    @Binds
    @OpenWeatherRepo
    abstract fun bindOpenWeatherRepo(impl: OpenWeatheRepositoryImpl): WeatherRepository


}
@Qualifier
annotation class NinjaRepo

@Qualifier
annotation class OpenWeatherRepo

@Qualifier
annotation class OpenMeteoRepo
