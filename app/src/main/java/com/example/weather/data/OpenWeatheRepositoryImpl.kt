package com.example.weather.data

import com.example.weather.domain.WeatherData
import com.example.weather.presentation.main.MainFragment.Companion.OPEN_WEATHER_API_KEY
import com.example.weather.retrofit.openWeather.GeocodingDTO
import com.example.weather.retrofit.openWeather.OpenWeatherCommon
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

object OpenWeatheRepositoryImpl : WeatherRepository {
    val myService = OpenWeatherCommon.retrofitService
    val mapper = Mappers()
    override fun getCityByName(cityName: String): Single<List<GeocodingDTO>> {
        return myService.getCoordByName(cityName, appId = OPEN_WEATHER_API_KEY)
            .subscribeOn(Schedulers.io())
    }

    override fun getWeather(lat: String, lon: String): Single<WeatherData> {
        val data = myService.getForecastByCoorddinates(
            latitude = lat,
            longitude = lon,
            appId = OPEN_WEATHER_API_KEY,
            units = "metric",
            lang = "ru",
//            nDays = 25
        )
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.computation())
            .map ( mapper::mapOpenForecastToCWeatherData )
        return data
    }
}

