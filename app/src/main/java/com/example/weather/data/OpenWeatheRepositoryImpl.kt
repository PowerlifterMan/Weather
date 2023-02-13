package com.example.weather.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.weather.domain.OpenWeatherRepository
import com.example.weather.domain.RecyclerViewItem
import com.example.weather.presentation.main.MainFragment
import com.example.weather.retrofit.OpenWeatherDto
import com.example.weather.retrofit.openWeather.OpenWeatherCommon
import com.example.weather.retrofit.openWeather.OpenWeatherForecastDTO
import retrofit2.Call
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt

object OpenWeatheRepositoryImpl : OpenWeatherRepository {
    val myService = OpenWeatherCommon.retrofitService
    val weatherOnEachDay = MutableLiveData<OpenWeatherForecastDTO>()
    val currentWeather = MutableLiveData<OpenWeatherDto>()

    override fun getForecastOpenWeather(
        lat: String,
        lon: String
    ): LiveData<OpenWeatherForecastDTO> {
        myService.getForecastByCoorddinates(
            latitude = lat,
            longitude = lon,
            appId = MainFragment.OPEN_WEATHER_API_KEY,
            units = "metric",
            lang = "ru",
            nDays = 8
        ).enqueue(object : retrofit2.Callback<OpenWeatherForecastDTO> {
            override fun onFailure(call: Call<OpenWeatherForecastDTO>, throwable: Throwable) {
                Log.d("AAAA", "ОШИБКА!!!")
            }

            override fun onResponse(
                call: Call<OpenWeatherForecastDTO>,
                response: Response<OpenWeatherForecastDTO>
            ) {
                weatherOnEachDay.value = response.body()
            }
        })
        return weatherOnEachDay
    }

    override fun getWeatherOpenWeather(lat: String, lon: String): LiveData<OpenWeatherDto> {
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
                currentWeather.value = response.body()
            }
        })
        return currentWeather
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