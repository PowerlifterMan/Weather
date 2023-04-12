package com.example.weather.di

import com.example.weather.presentation.main.InputPlaceFragment
import com.example.weather.presentation.main.InputPlaceViewModel
import dagger.Component
import dagger.Module

@Component(modules = [WeatherModule::class])
interface WeatherComponent {

    fun inject(inputPlaceFragment: InputPlaceFragment)
}