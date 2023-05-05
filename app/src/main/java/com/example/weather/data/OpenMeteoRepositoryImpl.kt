package com.example.weather.data

import android.util.Log
import com.example.weather.OpenMeteo.OpenMeteoCommon
import com.example.weather.data.dto.Mappers
import com.example.weather.data.room.AppDataBase
import com.example.weather.data.room.ForecastDbModel
import com.example.weather.domain.WeatherData
import com.example.weather.presentation.main.SOURCE_OPEN_METEO
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import java.text.DecimalFormat
import javax.inject.Inject

class OpenMeteoRepositoryImpl @Inject constructor(
    appDataBase: AppDataBase
) : WeatherRepository {
    val currentSourceName = SOURCE_OPEN_METEO
    val service = OpenMeteoCommon.retrofitService
    val mapper = Mappers()
    private val weatherForecastDao =
        appDataBase.weatherForecastDao()
    var newCityKladr = ""
    private lateinit var currentCityName: String
    private var lonOpenMeteo: Float? = null
    private var latOpenMeteo: Float? = null

    override fun getWeather(
        lat: Float,
        lon: Float,
        cityName: String,
        cityKladr: String
    ): Single<WeatherData> {
        currentCityName = cityName
        latOpenMeteo = lat
        lonOpenMeteo = lon
        newCityKladr = cityKladr
        return if (needToUpdate()) {
//            weatherForecastDao.clearData(sourceId = currentSourceName, cityId = cityName)
//            weatherForecastDao.updateDataSet(sourceId = currentSourceName)
            getWeatherFromRemote(lat = lat, lon = lon)
                .andThen(getWeatherFromLocal(cityName = cityName))
        } else getWeatherFromLocal(cityName = cityName)
            .filter { it.forecastList.isEmpty() }.toSingle()
    }

    private fun getWeatherFromRemote(lat: Float, lon: Float): Completable {
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
            .flatMapCompletable {
                saveWeatherToLocal(it)
            }

        return data
    }


    private fun getWeatherFromLocal(lat: Float, lon: Float): Single<WeatherData> {
        val weatherList = weatherForecastDao.getWeatherList(
            currentSourceName, lat = lat, lon = lon
        )
        var weatherData: WeatherData? = null
        if (weatherList.size > 0) {
            weatherData =
                mapper.mapForecastDbModelListToWeatherData(weatherList)
        } else {
            weatherData = WeatherData(cityName = "", cityLongitude = lon, cityLatitude = lat)
        }
        return Single.fromCallable {
            weatherData
        }
    }

    private fun getWeatherFromLocal(cityName: String): Single<WeatherData> {
        return Single.fromCallable {
            val weatherList = weatherForecastDao.getWeatherList(
                cityName = cityName.trim(),
                sourceId = currentSourceName.trim()
            )
            Log.e("ERROR", "$cityName данные запрошены")
            var weatherData: WeatherData? = null
            if (weatherList.size > 0) {
                weatherData =
                    mapper.mapForecastDbModelListToWeatherData(weatherList)
            } else {
                weatherData = WeatherData(
                    cityName = cityName,
                    cityLongitude = lonOpenMeteo,
                    cityLatitude = latOpenMeteo

                )
                Log.e("ERROR", "$currentCityName WeatherData is EMPTY")

            }
            weatherData
        }
    }

    private fun saveWeatherToLocal(weatherData: WeatherData): Completable {
        val df = DecimalFormat("##.00")
        val latitude2 = weatherData.cityLatitude
        val longitude2 = weatherData.cityLongitude
        val weatherList = mutableListOf<ForecastDbModel>()
        if (weatherData.forecastList.isNotEmpty()) {
            weatherForecastDao.clearData(
                sourceId = currentSourceName,
//                cityId = currentCityName
            )
            Log.e("ERROR", "OpenMeteo затерли $currentSourceName ")

        }

        return Completable.fromCallable {
            weatherData.forecastList.forEach {
                val model = ForecastDbModel(
                    id = 0,
                    idCity = currentCityName,
                    idSource = currentSourceName,
                    latitude = latitude2,
                    longitude = longitude2,
                    timeStamp = it.timeStamp,
                    temperature = it.temperatureMax,
                    temperatureFeelsLike = it.temperatureFeelsLikeMax,
                    humidity = it.humidity,
                    weatherCondition = null,
                    weatherConditionIconId = null,
                    cityKladr = newCityKladr
                )
                weatherList.add(model)
            }
            weatherForecastDao.addForecastList(weatherList)
            Log.e("ERROR", "OpenMeteo recording $currentCityName List is complete")
            weatherForecastDao.updateDB(weatherList)
            Log.e("ERROR", "OpenMeteo Update $currentCityName List is complete")
        }
    }

    private fun needToUpdate(): Boolean {
        return true
    }

    override fun getCityByName(cityName: String): Single<List<GeocodingDTO>> {
        TODO("Not yet implemented")
    }
}