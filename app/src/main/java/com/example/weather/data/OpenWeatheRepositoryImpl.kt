package com.example.weather.data

import com.example.weather.data.dto.Mappers
import com.example.weather.domain.WeatherData
import com.example.weather.presentation.main.MainFragment.Companion.OPEN_WEATHER_API_KEY
import com.example.weather.retrofit.openWeather.OpenWeatherCommon
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

object OpenWeatheRepositoryImpl : WeatherRepository {
    val service = OpenWeatherCommon.retrofitService
    val mapper = Mappers()
    override fun getCityByName(cityName: String): Single<List<GeocodingDTO>> {
        return service.getCoordByName(cityName, appId = OPEN_WEATHER_API_KEY)
            .subscribeOn(Schedulers.io())
    }

    override fun getWeather(lat: Float, lon: Float): Single<WeatherData> {
        val data = service.getForecastByCoorddinates(
            latitude = lat.toString(),
            longitude = lon.toString(),
            appId = OPEN_WEATHER_API_KEY,
            units = "metric",
            lang = "ru",
//            nDays = 25
        )
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.computation())
            .map ( mapper::mapOpenForecastToWeatherData )
        return data
    }
}

