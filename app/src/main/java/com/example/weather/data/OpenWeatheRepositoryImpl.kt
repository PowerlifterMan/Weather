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

object OpenWeatheRepositoryImpl : WeatherRepository {
    val currentSourceName = SOURCE_OPEN_WEATHER
    val service = OpenWeatherCommon.retrofitService
    val mapper = Mappers()
    private val weatherForecastDao =
        AppDataBase.getInstance().weatherForecastDao()

    override fun getWeather(lat: Float, lon: Float): Single<WeatherData> {

        return if (checkLocalNeedToUpdate()) {
            getWeatherFromRemote(
                lat = lat,
                lon = lon
            )
                .andThen ( getWeatherFromLocal(lat = lat, lon = lon) )
        } else getWeatherFromLocal(lat = lat, lon = lon)
            .filter { it.forecastList.isEmpty() }.toSingle()
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
            nDays = 25
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

        }
        return Single.fromCallable {
            weatherData
        }


    }

    private fun saveWeatherToLocal(weatherData: WeatherData): Completable {
        return Completable.fromCallable {

            val latitude = weatherData.cityLatitude
            val longitude = weatherData.cityLongitude
            val cityName = weatherData.cityName
            weatherData.forecastList.forEach {
                val model = ForecastDbModel(
                    0,
                    idSource = currentSourceName,
                    latitude = latitude,
                    longitude = longitude,
                    timeStamp = it.timeStamp,
                    temperature = it.temperatureMax,
                    temperatureFeelsLike = it.temperatureFeelsLikeMax,
                    humidity = it.humidity
                )
                weatherForecastDao.addForecastItem(model)
            }
        }
    }

    private fun checkLocalNeedToUpdate(): Boolean {
        return true
    }
}

