package com.example.weather.ninjas

import com.example.weather.data.Mappers
import com.example.weather.domain.WeatherData
import com.example.weather.domain.WeatherRepository
import com.example.weather.retrofit.OpenWeatherDto
import com.example.weather.retrofit.openWeather.GeocodingDTO
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

object NinjasRepositoryImpl : WeatherRepository {
    val service = NinjasCommon.retrofitService
    val mapper = Mappers()

    override fun getWeather(lat: String, lon: String,): Single<WeatherData> {
        val data = service.getCurrentWeather(
            apiKey = "WqthQnLS3J9U8msOMh/iFw==7ZsAWxaBsOpJ9aaf",
            longitude = lon.toString(),
            latitude = lat.toString()
        ).subscribeOn(Schedulers.io())


    }

    override fun getCityByName(cityName: String): Single<List<GeocodingDTO>> {
        TODO("Not yet implemented")
    }

     fun getWeather1(lon: Float, lat: Float): Single<NinjasDTO> {
        return service.getCurrentWeather(
            apiKey = "WqthQnLS3J9U8msOMh/iFw==7ZsAWxaBsOpJ9aaf",
            longitude = lon.toString(),
            latitude = lat.toString()
        ).subscribeOn(Schedulers.io())
    }
}