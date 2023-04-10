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


object NinjasRepositoryImpl : WeatherRepository {
    val currentSourceName = SOURCE_NINJAS
    val service = NinjasCommon.retrofitService
    val mapper = Mappers()
    private val weatherForecastDao =
        AppDataBase.getInstance().weatherForecastDao()

    override fun getWeather(lat: Float, lon: Float,cityName: String): Single<WeatherData> {
        return if (needToUpdate()) {
            getWeatherFromRemote(lat = lat, lon = lon)
                .andThen(
                    getWeatherFromLocal(lat = lat,lon = lon)
                )
        } else getWeatherFromLocal(lat = lat, lon = lon)
    }

    private fun getWeatherFromLocal(lat: Float, lon: Float): Single<WeatherData> {
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
                    latitude = latitude,
                    longitude = longitude,
                    timeStamp = it.timeStamp,
                    temperature = (it.temperatureMax + it.temperatureMin) / 2,
                    temperatureFeelsLike = (it.temperatureFeelsLikeMax - it.temperatureFeelsLikeMin) / 2,
                    humidity = it.humidity
                )
                weatherForecastDao.addForecastItem(model)
            }

        }

    }

    private fun needToUpdate(): Boolean {
        return true
    }

    private fun getWeatherFromRemote(lat: Float, lon: Float): Completable {
        return service.getCurrentWeather(
            apiKey = "WqthQnLS3J9U8msOMh/iFw==7ZsAWxaBsOpJ9aaf",
            longitude = lon.toString(),
            latitude = lat.toString()
        )
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.computation())
            .map { mapper.mapNinjasDtoToWeatherData(it) }
            .flatMapCompletable { saveWeatherToLocal(it) }
    }

    override fun getCityByName(cityName: String): Single<List<GeocodingDTO>> {
        TODO("Not yet implemented")
    }
}