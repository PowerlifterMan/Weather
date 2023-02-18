package com.example.weather.presentation.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weather.data.OpenWeatheRepositoryImpl
import com.example.weather.domain.CityForecastData
import com.example.weather.domain.RecyclerViewItem
import com.example.weather.domain.TempOnTime
import com.example.weather.domain.WeatherUseCase
import java.text.SimpleDateFormat
import java.util.*

class MainViewModel : ViewModel() {
    private val repository = OpenWeatheRepositoryImpl
    private val openWeatherUseCase = WeatherUseCase(repository)

    //val currentResponce = openWeatherUseCase.getForecastOpenWeather()
    private val cityForecastLiveData = openWeatherUseCase.getOpenWeatherFOrecastData()
    val c: Int = 2

    val myCityName = MutableLiveData<String>()
    val cityForecastDataLD = MutableLiveData<CityForecastData>()
    val cityRow = MutableLiveData<String>()

    val rvRow = MutableLiveData<List<RecyclerViewItem>>()

    fun getCity(): LiveData<String> {
        cityForecastLiveData.value?.city?.name.let {
            cityRow.value = it
        }
        return cityRow
//        cityRow.value.let {  } = cityForecastLiveData.value?.city?.name
    }

    fun getList(): LiveData<List<RecyclerViewItem>> {
        val rvList = mutableListOf<RecyclerViewItem>()
        cityForecastLiveData.value?.forecastList.let {
            it?.forEach { data ->
                val item = RecyclerViewItem(dayNumber = data.timestamp.toString(),
                temperature = data.temp.toString(),
                    description = data.tempFeelsLike.toString()
                    )
                rvList.add(item)
            }
        }
        return rvRow
    }

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