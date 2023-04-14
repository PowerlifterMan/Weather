package com.example.weather.di

import android.content.Context
import com.example.weather.presentation.main.InputPlaceFragment
import com.example.weather.presentation.main.InputPlaceViewModel
import com.example.weather.presentation.main.MainFragment
import dagger.BindsInstance
import dagger.Component
import dagger.Module

@Component(modules = [WeatherModule::class,DataModule::class,ViewModelModule::class])
interface WeatherComponent {

    fun inject(inputPlaceFragment: InputPlaceFragment)

    fun inject(mainFragment: MainFragment)

    @Component.Factory
    interface WeatherComponentFactory{

        fun create(
            @BindsInstance context: Context,

            ): WeatherComponent
    }

}