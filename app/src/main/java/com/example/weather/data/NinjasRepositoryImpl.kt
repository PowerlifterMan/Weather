package com.example.weather.data

import com.example.weather.data.dto.Mappers
import com.example.weather.data.room.AppDataBase
import com.example.weather.domain.CurrentTemp
import com.example.weather.domain.WeatherData
import com.example.weather.ninjas.NinjasCommon
import com.example.weather.presentation.main.SOURCE_NINJAS
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers


object NinjasRepositoryImpl : WeatherRepository {
    val service = NinjasCommon.retrofitService
    val mapper = Mappers()
    private val weatherForecastDao =
        AppDataBase.getInstance().weatherForecastDao()

    override fun getWeather(lat: Float, lon: Float): Single<WeatherData> {
        return if (checkLocalNeedToUpdate()) {
            getWeatherFromRemote(lat = lat, lon = lon)
        } else getWeatherFromLocal(lat = lat, lon = lon)
    }

    private fun getWeatherFromLocal(lat: Float, lon: Float): Single<WeatherData> {
        val weatherList = weatherForecastDao.getWeatherList(SOURCE_NINJAS, lat = lat, lon = lon)
        var weatherData: WeatherData? = null
        if (weatherList.isNotEmpty()) {
            weatherData =
                mapper.mapForecastDbModelListToWeatherData(weatherList)
        } else {
            weatherData = WeatherData(
                cityLatitude = lat,
                cityLongitude = lon,
            )
        }
        return Single.fromCallable {
            weatherData
        }
    }

    private fun saveWeatherToLocal(weatherData: WeatherData) {

    }

    private fun checkLocalNeedToUpdate(): Boolean {
        return true
    }

    private fun getWeatherFromRemote(lat: Float, lon: Float): Single<WeatherData> =
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
