package com.example.weather.presentation.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weather.domain.CurrentCity
import com.example.weather.domain.RecyclerViewItem
import com.example.weather.retrofit.OpenWeatherDto
import com.example.weather.retrofit.openWeather.OpenWeatherCommon
import com.example.weather.retrofit.openWeather.OpenWeatherForecastDTO
import retrofit2.Call
import retrofit2.Response
import java.sql.Timestamp
import java.util.*

class MainViewModel : ViewModel() {
    val myService = OpenWeatherCommon.retrofitService
    val weatherOnEachDay =  MutableLiveData<List<RecyclerViewItem>>()
    private var currentCity: MutableLiveData<CurrentCity> =
        MutableLiveData<CurrentCity>()

    fun getCurrentCityName(): LiveData<CurrentCity> {
        return currentCity
    }
    fun provideList(){
    }

    fun getCurrentWeather(lat: String = "44.04", lon: String = "42.86", dayz: Int = 1) {
        myService.getWeatherByCoorddinates(
            latitude = lat,
            longitude = lon,
            appId = MainFragment.OPEN_WEATHER_API_KEY,
            units = "metric",
            lang = "ru",
            nDays = 1
        ).enqueue(object : retrofit2.Callback<OpenWeatherDto> {
            override fun onFailure(call: Call<OpenWeatherDto>, throwable: Throwable) {
                Log.d("AAAA", "ОШИБКА!!!")
            }

            override fun onResponse(
                call: Call<OpenWeatherDto>,
                response: Response<OpenWeatherDto>
            ) {
            }
        })
    }

}