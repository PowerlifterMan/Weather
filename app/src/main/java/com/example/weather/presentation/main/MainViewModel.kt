package com.example.weather.presentation.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weather.data.Mappers
import com.example.weather.data.OpenWeatheRepositoryImpl
import com.example.weather.domain.RecyclerViewItem
import com.example.weather.domain.TempOnTime
import com.example.weather.domain.WeatherUseCase
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import java.text.SimpleDateFormat
import java.util.*

class MainViewModel : ViewModel() {
    private val repository = OpenWeatheRepositoryImpl
    private val openWeatherUseCase = WeatherUseCase(repository)
    val sdf = SimpleDateFormat("MM/dd-hh")

    //val currentResponce = openWeatherUseCase.getForecastOpenWeather()
    val c: Int = 2

    private val myCityName = MutableLiveData<String>()
    private val myCityCurrentWeather = MutableLiveData<TempOnTime>()
    val cityRow = MutableLiveData<String>()
    val rvRow = MutableLiveData<List<RecyclerViewItem>>()

    val mapper = Mappers()

    fun getCity(): LiveData<String> {
        return myCityName
    }

    fun getCurrentWeather(): LiveData<TempOnTime> {
        return myCityCurrentWeather
    }

    fun getForecast(): LiveData<List<RecyclerViewItem>> {
        return rvRow
    }

    fun getForecastData() {
        openWeatherUseCase.getWeatherOpenWeather(lat = "55.75", lon = "37.61")
            .observeOn(Schedulers.computation())
            .map(mapper::mapOpenWeatherToCurrentWeather)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { data ->
                myCityName.value = data.city.name
                myCityCurrentWeather.value = data.forecastList[0]
            }

        openWeatherUseCase.getOpenWeatherFOrecastData(lat = "55.75", lon = "37.61")
            .observeOn(Schedulers.computation())
            .map(mapper::mapOpenForecastToCityForecast)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ data ->
                cityRow.value = data.city.name
                rvRow.value = data.forecastList.map { item ->
                    RecyclerViewItem(
                        dayNumber = sdf.format(item.timestamp * 1000),
                        temperature = Math.round(item.temp).toString(),
                        description = Math.round(item.tempFeelsLike).toString()
                    )
                }
            }, {
                it.printStackTrace()
            })
    }

    private fun getDateTime(s: Long): String? {
        try {
            val sdf = SimpleDateFormat("yyyy/MM/dd")
            val netDate = Date(s * 1000)
            return sdf.format(netDate)
        } catch (e: Exception) {
            return e.toString()
        }
    }

}