package com.example.weather.presentation.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import javax.inject.Inject

class Settings2ViewModel @Inject constructor(): ViewModel() {
    var isErrorState = MutableLiveData<Boolean>()
        private set

    fun checkErrorState(){

    }
}