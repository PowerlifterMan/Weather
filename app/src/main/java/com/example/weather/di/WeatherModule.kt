package com.example.weather.di

import com.example.weather.data.NinjasRepositoryImpl
import com.example.weather.data.OpenMeteoRepositoryImpl
import com.example.weather.data.OpenWeatheRepositoryImpl
import com.example.weather.data.WeatherRepository
import com.example.weather.domain.WeatherUseCase
import com.example.weather.presentation.main.InputPlaceFragment
import com.example.weather.presentation.main.MainFragment
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
abstract class  WeatherModule {
    companion object{
        @Singleton
        @Provides
        fun provideWeatherUseCase(
            @NinjaRepo
            ninjaRepo: WeatherRepository,

            @OpenMeteoRepo
            openMeteoRepo: WeatherRepository,

            @OpenWeatherRepo
            openWeatherRepo: WeatherRepository
        ): WeatherUseCase {
            return WeatherUseCase(
                ninjaRepo, openMeteoRepo, openWeatherRepo
            )
        }
    }
//    @Singleton
//    @Binds
//    abstract fun bindWeatherUseCase(impl: WeatherUseCase): WeatherUseCase

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

    @ContributesAndroidInjector
    abstract fun mainFragment():MainFragment

    @ContributesAndroidInjector
    abstract fun inputPlaceFragment():InputPlaceFragment

//    @ContributesAndroidInjector
//    abstract fun mainFragment():MainFragment
}

@Qualifier
annotation class NinjaRepo

@Qualifier
annotation class OpenWeatherRepo

@Qualifier
annotation class OpenMeteoRepo
