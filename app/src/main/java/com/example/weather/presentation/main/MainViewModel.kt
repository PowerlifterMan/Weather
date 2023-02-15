package com.example.weather.presentation.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weather.data.OpenWeatheRepositoryImpl
import com.example.weather.domain.CityForecastData
import com.example.weather.domain.RecyclerViewItem
import com.example.weather.domain.WeatherUseCase
import java.text.SimpleDateFormat
import java.util.*

class MainViewModel : ViewModel() {
    private val repository = OpenWeatheRepositoryImpl
    private val openWeatherUseCase = WeatherUseCase(repository)
    val currentResponce = openWeatherUseCase.getForecastOpenWeather()
    val myCityName = MutableLiveData<String>()
    val cityForecastDataLD = MutableLiveData<CityForecastData>()
    val cityRow = MutableLiveData<String>()
    val rvRow = MutableLiveData<List<RecyclerViewItem>>()

    private fun getDateTime(s: Long): String? {
        try {
            val sdf = SimpleDateFormat("MM/dd-hh")
            val netDate = Date(s * 1000)
            return sdf.format(netDate)
        } catch (e: Exception) {
            return e.toString()
        }
    }
}