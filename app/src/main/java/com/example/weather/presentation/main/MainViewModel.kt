package com.example.weather.presentation.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weather.data.OpenWeatheRepositoryImpl
import com.example.weather.domain.CurrentCity
import com.example.weather.domain.RecyclerViewItem
import com.example.weather.domain.WeatherUseCase
import com.example.weather.retrofit.OpenWeatherDto
import com.example.weather.retrofit.openWeather.OpenWeatherCommon
import com.example.weather.retrofit.openWeather.OpenWeatherForecastDTO
import retrofit2.Call
import retrofit2.Response
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt

class MainViewModel : ViewModel() {
    private val repository = OpenWeatheRepositoryImpl
    private val openWeatherUseCase = WeatherUseCase(repository)
    val currentResponce = openWeatherUseCase.getForecastOpenWeather()
    val myService = OpenWeatherCommon.retrofitService
    val weatherOnEachDay = MutableLiveData<List<RecyclerViewItem>>()
    val myCityName = MutableLiveData<String>()

    private var currentCity: MutableLiveData<CurrentCity> =
        MutableLiveData<CurrentCity>()

    fun getCurrentCityName(): LiveData<CurrentCity> {
        return currentCity
    }

    fun getResponceList() {

    }

    fun provideList() {
    }

//    fun getForecast(lat: String = "44.044", lon: String = "42.86") {
//        val rvList = mutableListOf<RecyclerViewItem>()
//        myService.getForecastByCoorddinates(
//            latitude = lat,
//            longitude = lon,
//            appId = MainFragment.OPEN_WEATHER_API_KEY,
//            units = "metric",
//            lang = "ru",
//            nDays = 8
//        ).enqueue(object : retrofit2.Callback<OpenWeatherForecastDTO> {
//            override fun onFailure(call: Call<OpenWeatherForecastDTO>, throwable: Throwable) {
//                Log.d("AAAA", "ОШИБКА!!!")
//            }
//
//            override fun onResponse(
//                call: Call<OpenWeatherForecastDTO>,
//                response: Response<OpenWeatherForecastDTO>
//            ) {
//                myCityName.value = response.body()?.city?.cityName
//                val forecast = response.body()?.list
//                forecast?.forEach {
//                    val date = getDateTime(it.dateOfForecast)
//                    rvList.add(
//                        RecyclerViewItem(
//                            dayNumber = date.toString(),
//                            temperature = it.mainForecastData.temp.roundToInt().toString(),
//                            description = it.mainForecastData.tempFeels.roundToInt().toString()
//
//                        )
//                    )
//                }
//                weatherOnEachDay.value = rvList
//            }
//        })
//
//    }

    fun getCurrentWeather(lat: String = "44.04", lon: String = "42.86") {
        myService.getWeatherByCoorddinates(
            latitude = lat,
            longitude = lon,
            appId = MainFragment.OPEN_WEATHER_API_KEY,
            units = "metric",
            lang = "ru",

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