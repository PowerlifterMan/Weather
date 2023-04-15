package com.example.weather.di

import android.app.Application
import android.content.Context
import com.example.weather.WeatherApp
import com.example.weather.presentation.main.InputPlaceFragment
import com.example.weather.presentation.main.MainFragment
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import javax.inject.Singleton

@Singleton
@Component(modules = [
    WeatherModule::class,
    DataBaseModule::class,
    ViewModelModule::class,
    AndroidInjectionModule::class
])
interface WeatherComponent: AndroidInjector<WeatherApp> {

    fun inject(inputPlaceFragment: InputPlaceFragment)

    fun inject(mainFragment: MainFragment)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        @BindsInstance
        fun withContext(context: Context): Builder

        fun build(): WeatherComponent
    }
//    @Component.Factory
//    interface WeatherComponentFactory{
//
//        fun create(
//            @BindsInstance context: Context,
//
//            ): WeatherComponent
//    }

}