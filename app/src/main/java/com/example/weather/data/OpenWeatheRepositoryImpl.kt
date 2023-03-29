package com.example.weather.data

import android.util.Log
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
import java.text.DecimalFormat

object OpenWeatheRepositoryImpl : WeatherRepository {
    val currentSourceName = SOURCE_OPEN_WEATHER
    lateinit var currentCity: String
    val service = OpenWeatherCommon.retrofitService
    val mapper = Mappers()
    private val weatherForecastDao =
        AppDataBase.getInstance().weatherForecastDao()

    override fun getWeather(lat: Float, lon: Float, cityName: String): Single<WeatherData> {
        currentCity = cityName
        return if (needToUpdate()) {
            Completable.fromCallable {
//                weatherForecastDao.clearData(sourceId = currentSourceName, cityId = cityName)
            }.andThen(getWeatherFromRemote(lat = lat, lon = lon))
                .andThen(getWeatherFromLocal(cityName = cityName))
        } else getWeatherFromLocal(cityName = cityName)
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
        val weatherList =
            weatherForecastDao.getWeatherList(lon = lon, lat = lat, sourceId = currentSourceName)
        Log.e("ERROR", weatherList.toString())
        var weatherData: WeatherData? = null
        if (weatherList.size > 0) {
            weatherData = mapper.mapForecastDbModelListToWeatherData(weatherList)
        } else {
            weatherData =
                WeatherData(cityName = currentCity, cityLongitude = lon, cityLatitude = lat)
        }
        return Single.fromCallable {
            weatherData
        }


    }

    private fun getWeatherFromLocal(cityName: String): Single<WeatherData> {
        val weatherList =
            weatherForecastDao.getWeatherList(
                cityName = cityName,
                sourceId = currentSourceName
            )
        Log.e("ERROR", weatherList.toString())
        var weatherData: WeatherData? = null
        if (weatherList.size > 0) {
            weatherData = mapper.mapForecastDbModelListToWeatherData(weatherList)
        } else {
            weatherData = WeatherData(cityName = currentCity, cityLongitude = 0f, cityLatitude = 0f)
        }
        return Single.fromCallable {
            weatherData
        }


    }

    private fun saveWeatherToLocal(weatherData: WeatherData): Completable {
        val df = DecimalFormat("##.00")
        val latitude2 = weatherData.cityLatitude
        val longitude2 = weatherData.cityLongitude
        val weatherList = mutableListOf<ForecastDbModel>()
        if (weatherData.forecastList.isNotEmpty()){
            weatherForecastDao.clearData(sourceId = currentSourceName, cityId = weatherData.cityName)
        }
        val cityName = weatherData.cityName
        return Completable.fromCallable {
            weatherData.forecastList.forEach {
                val model = ForecastDbModel(
                    id = 0,
                    idCity = cityName,
                    idSource = currentSourceName,
                    latitude = latitude2,
                    longitude = longitude2,
                    timeStamp = it.timeStamp,
                    temperature = (it.temperatureMax + it.temperatureMin) / 2,
                    temperatureFeelsLike = (it.temperatureFeelsLikeMax + it.temperatureFeelsLikeMin) / 2,
                    humidity = it.humidity,
                )
                weatherList.add(model)
            }
            weatherForecastDao.addForecastList(weatherList)
            Log.e("ERROR", "recording List is complete")
        }
    }

    private fun needToUpdate(): Boolean {
        return true
    }

    override fun getCityByName(cityName: String): Single<List<GeocodingDTO>> {
        return service.getCoordByName(cityName, appId = OPEN_WEATHER_API_KEY)
            .subscribeOn(Schedulers.io())
    }
}

