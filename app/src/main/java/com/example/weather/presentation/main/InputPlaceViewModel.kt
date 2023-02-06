package com.example.weather.presentation.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class InputPlaceViewModel:ViewModel() {
    val message: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

}
