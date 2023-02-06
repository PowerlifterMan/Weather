package com.example.weather.presentation.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weather.domain.CurrentCity

class MainViewModel : ViewModel() {
   private  var currentCity: MutableLiveData<CurrentCity> =
         MutableLiveData<CurrentCity>()

   fun getCurrentCityName(): LiveData<CurrentCity> {
//      return currentCity.value?.name ?: "Москва"
       return currentCity
   }

}