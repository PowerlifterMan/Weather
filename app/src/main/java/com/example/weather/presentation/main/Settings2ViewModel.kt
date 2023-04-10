package com.example.weather.presentation.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class Settings2ViewModel: ViewModel() {
    var isErrorState = MutableLiveData<Boolean>()
        private set

    fun checkErrorState(){

    }
}