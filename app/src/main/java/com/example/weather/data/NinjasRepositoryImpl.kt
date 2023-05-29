package com.example.weather.data

import com.example.weather.data.dto.Mappers
import com.example.weather.data.room.AppDataBase
import com.example.weather.data.room.ForecastDbModel
import com.example.weather.domain.WeatherData
import com.example.weather.ninjas.NinjasCommon
import com.example.weather.presentation.main.SOURCE_NINJAS
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject


class NinjasRepositoryImpl @Inject constructor(
    appDataBase: AppDataBase

) : WeatherRepository {
    val currentSourceName = SOURCE_NINJAS
    val service = NinjasCommon.retrofitService
    val mapper = Mappers()
    private val weatherForecastDao =
        appDataBase.weatherForecastDao()

    override suspend fun getWeather(
        lat: Float,
        lon: Float,
        cityName: String,
        cityKladr: String
    ): WeatherData {
        if (needToUpdate()) {
            getWeatherFromRemote(lat = lat, lon = lon)
        }
        return getWeatherFromLocal(lat = lat, lon = lon)

    }

    private suspend fun getWeatherFromLocal(lat: Float, lon: Float): WeatherData {
        val weatherList = weatherForecastDao.getWeatherList(currentSourceName, lat = lat, lon = lon)
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
        return weatherData

    }

    private suspend fun saveWeatherToLocal(weatherData: WeatherData) {
        val latitude = weatherData.cityLatitude
        val longitude = weatherData.cityLongitude
        val cityName = weatherData.cityName
        val listForSave = weatherData.forecastList.map { it ->
            ForecastDbModel(
                id = 0,
                idCity = cityName,
                idSource = currentSourceName,
                latitude = latitude,
                longitude = longitude,
                timeStamp = it.timeStamp,
                temperature = (it.temperatureMax + it.temperatureMin) / 2,
                temperatureFeelsLike = (it.temperatureFeelsLikeMax - it.temperatureFeelsLikeMin) / 2,
                humidity = it.humidity,
                weatherCondition = null,
                weatherConditionIconId = null,
                cityKladr = null
            )
        }
        weatherForecastDao.addForecastList(listForSave)
    }

    private fun needToUpdate(): Boolean {
        return true
    }

    private suspend fun getWeatherFromRemote(lat: Float, lon: Float) {
        val remoteWeather = service.getCurrentWeather(
            apiKey = "WqthQnLS3J9U8msOMh/iFw==7ZsAWxaBsOpJ9aaf",
            longitude = lon.toString(),
            latitude = lat.toString()
        )
        val data = mapper.mapNinjasDtoToWeatherData(remoteWeather)
        saveWeatherToLocal(data)
        /*
    .subscribeOn(Schedulers.io())
    .observeOn(Schedulers.computation())
    .map { mapper.mapNinjasDtoToWeatherData(it) }
    .flatMapCompletable { saveWeatherToLocal(it) } */
    }

    override suspend fun getCityByName(cityName: String): List<GeocodingDTO> {
        TODO("Not yet implemented")
    }
}