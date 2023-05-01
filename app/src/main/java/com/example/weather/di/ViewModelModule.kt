package com.example.weather.di

import androidx.lifecycle.ViewModel
import com.example.weather.presentation.main.DadataFragmentViewModel
import com.example.weather.presentation.main.InputPlaceViewModel
import com.example.weather.presentation.main.MainViewModel
import com.example.weather.presentation.main.Settings2ViewModel
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
    @ViewModelKey(DadataFragmentViewModel::class)
    @Binds
    fun bindDadataViewModel(impl: DadataFragmentViewModel): ViewModel

    @IntoMap
    @ViewModelKey(InputPlaceViewModel::class)
    @Binds
    fun bindInputPlaceViewModel(impl: InputPlaceViewModel): ViewModel

    @IntoMap
    @ViewModelKey(Settings2ViewModel::class)
    @Binds
    fun bindSettingsViewModel(impl: Settings2ViewModel): ViewModel
}