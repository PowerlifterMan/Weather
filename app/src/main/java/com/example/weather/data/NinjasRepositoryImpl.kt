package com.example.weather.data

import com.example.weather.data.dto.Mappers
import com.example.weather.domain.WeatherData
import com.example.weather.ninjas.NinjasCommon
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers


object NinjasRepositoryImpl : WeatherRepository {
    val service = NinjasCommon.retrofitService
    val mapper = Mappers()

    override fun getWeather(lat: Float, lon: Float): Single<WeatherData> =
        service.getCurrentWeather(
            apiKey = "WqthQnLS3J9U8msOMh/iFw==7ZsAWxaBsOpJ9aaf",
            longitude = lon.toString(),
            latitude = lat.toString()
        )
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.computation())
            .map { mapper.mapNinjasDtoToWeatherData(it) }

    override fun getCityByName(cityName: String): Single<List<GeocodingDTO>> {
        TODO("Not yet implemented")
    }
}


//     fun getWeather1(lon: Float, lat: Float): Single<NinjasDTO> {
//        return service.getCurrentWeather(
//            apiKey = "WqthQnLS3J9U8msOMh/iFw==7ZsAWxaBsOpJ9aaf",
//            longitude = lon.toString(),
//            latitude = lat.toString()
//        ).subscribeOn(Schedulers.io())
//    }
