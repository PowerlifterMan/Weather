package com.example.weather.OpenMeteo

import com.example.weather.domain.WeatherRepository
import com.example.weather.retrofit.OpenWeatherDto
import com.example.weather.retrofit.openWeather.GeocodingDTO
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

object OpenMeteoRepositoryImpl : WeatherRepository {

    val myService = OpenMeteoCommon.retrofitService

    override fun getWeather(lat: String, lon: String): Single<OpenWeatherDto> {
        return myService.getOpenMeteoForecast(
            latitude = lat,
            longitude = lon,
//            hourly = "temperature_2m",
            daily = "temperature_2m_max,apparent_temperature_max",
            timezone = "Europe/Moscow",
            current_weather = true,
            timeformat = "unixtime"
        ).subscribeOn(Schedulers.io())
    }

    override fun getCityByName(cityName: String): Single<List<GeocodingDTO>> {
        TODO("Not yet implemented")
    }

    fun getWeatherOpenMeteo(lat: Float, lon: Float): Single<OpenMeteoDTO> {
        return myService.getOpenMeteoForecast(
            latitude = lat,
            longitude = lon,
//            hourly = "temperature_2m",
            daily = "temperature_2m_max,apparent_temperature_max",
            timezone = "Europe/Moscow",
            current_weather = true,
            timeformat = "unixtime"
        ).subscribeOn(Schedulers.io())
    }

}