package com.example.weather.presentation.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SettingsViewModel : ViewModel() {
    val forecastSource = MutableLiveData<String>()

    fun getSource(): LiveData<String> {
        return forecastSource
    }

    fun setSource(value: String) {
        forecastSource.value = value
    }

}