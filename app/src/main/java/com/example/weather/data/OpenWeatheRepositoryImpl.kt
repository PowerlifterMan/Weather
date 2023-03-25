package com.example.weather.data

import com.example.weather.data.dto.Mappers
import com.example.weather.data.room.AppDataBase
import com.example.weather.data.room.ForecastDbModel
import com.example.weather.domain.WeatherData
import com.example.weather.presentation.main.MainFragment.Companion.OPEN_WEATHER_API_KEY
import com.example.weather.presentation.main.SOURCE_OPEN_WEATHER
import com.example.weather.retrofit.openWeather.OpenWeatherCommon
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlin.math.round

object OpenWeatheRepositoryImpl : WeatherRepository {
    val currentSourceName = SOURCE_OPEN_WEATHER
    val service = OpenWeatherCommon.retrofitService
    val mapper = Mappers()
    private val weatherForecastDao =
        AppDataBase.getInstance().weatherForecastDao()

    override fun getWeather(lat: Float, lon: Float): Single<WeatherData> {
        return if (needToUpdate()) {
            weatherForecastDao.clearData(sourceId = currentSourceName, lat = lat, lon = lon)
            getWeatherFromRemote(lat = lat, lon = lon)
                .andThen(getWeatherFromLocal(lat = lat, lon = lon))
        } else getWeatherFromLocal(lat = lat, lon = lon)
            .filter { it.forecastList.isEmpty() }.toSingle()
/*
        return if (needToUpdate()) {
            weatherForecastDao.clearData(sourceId = currentSourceName, lat = lat, lon = lon)
            getWeatherFromRemote(
                lat = lat,
                lon = lon
            )
                .andThen(getWeatherFromLocal(lat = lat, lon = lon))
        } else getWeatherFromLocal(lat = lat, lon = lon)
            .filter { it.forecastList.isEmpty() }.toSingle()

 */

    }

    override fun getCityByName(cityName: String): Single<List<GeocodingDTO>> {
        return service.getCoordByName(cityName, appId = OPEN_WEATHER_API_KEY)
            .subscribeOn(Schedulers.io())
    }

    private fun getWeatherFromRemote(lat: Float, lon: Float): Completable {
        val data = service.getForecastByCoorddinates(
            latitude = lat.toString(),
            longitude = lon.toString(),
            appId = OPEN_WEATHER_API_KEY,
            units = "metric",
            lang = "ru",
            nDays = 5
        )
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.computation())
            .map(mapper::mapOpenForecastToWeatherData)
            .flatMapCompletable { saveWeatherToLocal(it) }
        return data
    }

    private fun getWeatherFromLocal(lat: Float, lon: Float): Single<WeatherData> {
        val weatherList = weatherForecastDao.getWeatherList(currentSourceName, lat = lat, lon = lon)
        var weatherData: WeatherData? = null
        if (weatherList.size > 0) {
            weatherData = mapper.mapForecastDbModelListToWeatherData(weatherList)
        } else {
            weatherData = WeatherData(cityName = "", cityLongitude = lon, cityLatitude = lat)
        }
        return Single.fromCallable {
            weatherData
        }


    }

    private fun saveWeatherToLocal(weatherData: WeatherData): Completable {
        val latitude = weatherData.cityLatitude
        val longitude = weatherData.cityLongitude
        val cityName = weatherData.cityName
        return Completable.fromCallable {
            weatherData.forecastList.forEach {
                val model = ForecastDbModel(
                    id = 0,
                    idCity = cityName,
                    idSource = currentSourceName,
                    latitude = round(latitude * 100) / 100,
                    longitude = round(longitude * 100) / 100,
                    timeStamp = it.timeStamp,
                    temperature = round(it.temperatureMax * 10) / 10,
                    temperatureFeelsLike = round(it.temperatureFeelsLikeMax + 10) / 10,
                    humidity = it.humidity,
                )
                weatherForecastDao.addForecastItem(model)
            }
        }
    }

    private fun needToUpdate(): Boolean {
        return true
    }
}

