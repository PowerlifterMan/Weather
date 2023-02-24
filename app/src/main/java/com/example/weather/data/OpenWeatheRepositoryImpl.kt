package com.example.weather.data

import androidx.lifecycle.MutableLiveData
import com.example.weather.domain.OpenWeatherRepository
import com.example.weather.presentation.main.MainFragment
import com.example.weather.retrofit.OpenWeatherDto
import com.example.weather.retrofit.openWeather.OpenWeatherCommon
import com.example.weather.retrofit.openWeather.OpenWeatherForecastDTO
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import java.text.SimpleDateFormat
import java.util.*

object OpenWeatheRepositoryImpl : OpenWeatherRepository {
    val myService = OpenWeatherCommon.retrofitService
    val weatherOnEachDay = MutableLiveData<OpenWeatherForecastDTO>()
    val currentWeather = MutableLiveData<OpenWeatherDto>()

    override fun getForecastOpenWeather(
        lat: String,
        lon: String
    ): Single<OpenWeatherForecastDTO> {
        val data = myService.getForecastByCoorddinates(
            latitude = lat,
            longitude = lon,
            appId = MainFragment.OPEN_WEATHER_API_KEY,
            units = "metric",
            lang = "ru",
            nDays = 9
        ).subscribeOn(Schedulers.io())

        return  data
    }

    override fun getWeatherOpenWeather(lat: String, lon: String): Single<OpenWeatherDto> {
       return myService.getWeatherByCoorddinates(
            latitude = lat,
            longitude = lon,
            appId = MainFragment.OPEN_WEATHER_API_KEY,
            units = "metric",
            lang = "ru",
        )
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