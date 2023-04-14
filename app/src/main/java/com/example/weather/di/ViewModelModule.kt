package com.example.weather.di

import androidx.lifecycle.ViewModel
import com.example.weather.presentation.main.InputPlaceViewModel
import com.example.weather.presentation.main.MainViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import dagger.multibindings.StringKey

@Module
interface ViewModelModule {

    @IntoMap
    @ViewModelKey(MainViewModel::class)
    @Binds
    fun bindMainViewModel(impl: MainViewModel): ViewModel

    @IntoMap
    @ViewModelKey(InputPlaceViewModel::class)
    @Binds
    fun bindInputPlaceViewModel(impl: InputPlaceViewModel): ViewModel
}