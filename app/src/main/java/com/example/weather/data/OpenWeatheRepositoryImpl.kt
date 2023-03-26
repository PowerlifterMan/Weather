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
import java.math.RoundingMode
import java.text.DecimalFormat

object OpenWeatheRepositoryImpl : WeatherRepository {
    val currentSourceName = SOURCE_OPEN_WEATHER
    val service = OpenWeatherCommon.retrofitService
    val mapper = Mappers()
    private val weatherForecastDao =
        AppDataBase.getInstance().weatherForecastDao()

    override fun getWeather(lat: Float, lon: Float, cityName: String): Single<WeatherData> {
        return if (needToUpdate()) {
            weatherForecastDao.clearData(sourceId = currentSourceName)
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
        val df = DecimalFormat("#.##")
//        val roundoff = df.format(random)
//        val latitude = df.format(weatherData.cityLatitude).toFloat()
        val latitude = weatherData.cityLatitude?.toBigDecimal()?.setScale(2)?.toFloat()
        val longitude = weatherData.cityLongitude?.toBigDecimal()?.setScale(2)?.toFloat()
//        val longitude = df.format(weatherData.cityLongitude).toFloat()
        val cityName = weatherData.cityName
        return Completable.fromCallable {
            weatherData.forecastList.forEach {
                val model = ForecastDbModel(
                    id = 0,
                    idCity = cityName,
                    idSource = currentSourceName,
                    latitude =  latitude,
                    longitude = longitude,
                    timeStamp = it.timeStamp,
                    temperature = df.format(it.temperatureMax).toFloat(),
                    temperatureFeelsLike = df.format(it.temperatureFeelsLikeMax).toFloat(),
                    humidity = it.humidity,
                )
                weatherForecastDao.addForecastItem(model)
            }
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

