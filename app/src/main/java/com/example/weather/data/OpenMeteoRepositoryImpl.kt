package com.example.weather.data

import android.util.Log
import com.example.weather.OpenMeteo.OpenMeteoCommon
import com.example.weather.data.dto.Mappers
import com.example.weather.data.room.AppDataBase
import com.example.weather.data.room.ForecastDbModel
import com.example.weather.domain.WeatherData
import com.example.weather.presentation.main.SOURCE_OPEN_METEO
import java.text.DecimalFormat
import javax.inject.Inject

class OpenMeteoRepositoryImpl @Inject constructor(
    appDataBase: AppDataBase
) : WeatherRepository {
    val currentSourceName = SOURCE_OPEN_METEO
    private val dao = appDataBase.weatherForecastDao()
    val service = OpenMeteoCommon.retrofitService
    val mapper = Mappers()
    var newCityKladr = ""
    private lateinit var currentCityName: String
    private var lonOpenMeteo: Float? = null
    private var latOpenMeteo: Float? = null

    override suspend fun getWeather(
        lat: Float,
        lon: Float,
        cityName: String,
        cityKladr: String
    ): WeatherData {
        currentCityName = cityName
        latOpenMeteo = lat
        lonOpenMeteo = lon
        newCityKladr = cityKladr
        if (needToUpdate()) getWeatherFromRemote(lat = lat, lon = lon)

        return getWeatherFromLocal(cityName = cityName)
    }


    private suspend fun getWeatherFromRemote(lat: Float, lon: Float): WeatherData {
        val openMeteDto = service.getOpenMeteoForecast(
            latitude = lat,
            longitude = lon,
            daily = "temperature_2m_max,apparent_temperature_max",
            timezone = "Europe/Moscow",
            current_weather = true,
            timeformat = "unixtime"
        )
        val weatherData = mapper.mapOpenMeteoToWeatherData(openMeteDto)
        saveWeatherToLocal(weatherData)
        return weatherData
    }


    private suspend fun getWeatherFromLocal(cityName: String): WeatherData {
        val weatherList = dao.getWeatherList(
            cityName = cityName.trim(),
            sourceId = currentSourceName.trim()
        )
        return if (!weatherList.isNullOrEmpty()) {
            mapper.mapForecastDbModelListToWeatherData(weatherList)
        } else {
            WeatherData(
                cityName = cityName,
                cityLongitude = lonOpenMeteo,
                cityLatitude = latOpenMeteo
            )
        }


        /*  return Single.fromCallable {
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

         */

    }

    private suspend fun saveWeatherToLocal(weatherData: WeatherData) {
        val df = DecimalFormat("##.00")
        val latitude2 = weatherData.cityLatitude
        val longitude2 = weatherData.cityLongitude
        val weatherList = mutableListOf<ForecastDbModel>()
        if (weatherData.forecastList.isNotEmpty()) {
            dao.clearData(
                sourceId = currentSourceName,
            )
            Log.e("ERROR", "OpenMeteo затерли $currentSourceName ")
        }
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
        dao.addForecastList(weatherList)
        Log.e("ERROR", "OpenMeteo recording $currentCityName List is complete")
        dao.updateDB(weatherList)
        Log.e("ERROR", "OpenMeteo Update $currentCityName List is complete")
    }

    private fun needToUpdate(): Boolean {
        return true
    }

    override suspend fun getCityByName(cityName: String): List<GeocodingDTO> {
        TODO("Not yet implemented")
    }
}
