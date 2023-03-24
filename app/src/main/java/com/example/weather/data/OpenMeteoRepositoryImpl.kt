package com.example.weather.data

import com.example.weather.OpenMeteo.OpenMeteoCommon
import com.example.weather.data.dto.Mappers
import com.example.weather.domain.WeatherData
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

object OpenMeteoRepositoryImpl : WeatherRepository {
    val service = OpenMeteoCommon.retrofitService
    val mapper = Mappers()

    override fun getWeather(lat: Float, lon: Float): Single<WeatherData> {
        if (needToUpdate()){
            getWeatherFromRemote(lat = lat, lon = lon)
        }
        TODO("Not yet implemented")
    }

    private fun getWeatherFromRemote(lat: Float, lon: Float): Single<WeatherData> {
        val data = service.getOpenMeteoForecast(
            latitude = lat,
            longitude = lon,
//            hourly = "temperature_2m",
            daily = "temperature_2m_max,apparent_temperature_max",
            timezone = "Europe/Moscow",
            current_weather = true,
            timeformat = "unixtime"
        )
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.computation())
            .map(mapper::mapOpenMeteoToWeatherData)

//            .map { mapper.mapOpenMeteoToWeatherData(it) }
        return data
    }


    private fun getWeatherFromLocal(): Single<WeatherData> {
        TODO("Not yet implemented")
    }

    private fun saveWeatherToLocal(weatherData: WeatherData) {
        TODO("Not yet implemented")
    }

    private fun needToUpdate(): Boolean {
        return true
    }

    override fun getCityByName(cityName: String): Single<List<GeocodingDTO>> {
        TODO("Not yet implemented")
    }


//        return service.getOpenMeteoForecast(
//            latitude = lat,
//            longitude = lon,
////            hourly = "temperature_2m",
//            daily = "temperature_2m_max,apparent_temperature_max",
//            timezone = "Europe/Moscow",
//            current_weather = true,
//            timeformat = "unixtime"
//        ).subscribeOn(Schedulers.io())
//    }
//
//    override fun getCityByName(cityName: String): Single<List<GeocodingDTO>> {
//        TODO("Not yet implemented")
//    }

//    fun getWeatherOpenMeteo(lat: Float, lon: Float): Single<OpenMeteoDTO> {
//        return myService.getOpenMeteoForecast(
//            latitude = lat,
//            longitude = lon,
////            hourly = "temperature_2m",
//            daily = "temperature_2m_max,apparent_temperature_max",
//            timezone = "Europe/Moscow",
//            current_weather = true,
//            timeformat = "unixtime"
//        ).subscribeOn(Schedulers.io())
//    }

}