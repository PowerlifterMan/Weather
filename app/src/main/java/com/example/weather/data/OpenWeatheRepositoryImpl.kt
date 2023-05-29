package com.example.weather.data

import android.util.Log
import com.example.weather.data.dto.Mappers
import com.example.weather.data.room.AppDataBase
import com.example.weather.data.room.ForecastDbModel
import com.example.weather.domain.WeatherData
import com.example.weather.presentation.main.MainFragment.Companion.OPEN_WEATHER_API_KEY
import com.example.weather.presentation.main.SOURCE_OPEN_WEATHER
import com.example.weather.retrofit.openWeather.OpenWeatherCommon
import java.text.DecimalFormat
import javax.inject.Inject

class OpenWeatheRepositoryImpl @Inject constructor(
    appDataBase: AppDataBase
) : WeatherRepository {
    val currentSourceName = SOURCE_OPEN_WEATHER
    lateinit var currentCity: String
    val service = OpenWeatherCommon.retrofitService
    val mapper = Mappers()
    var newCityKladr = ""
    private val weatherForecastDao =
        appDataBase.weatherForecastDao()

    override suspend fun getWeather(
        lat: Float,
        lon: Float,
        cityName: String,
        cityKladr: String
    ): WeatherData {
        currentCity = cityName
        newCityKladr = cityKladr
        if (needToUpdate()) {
            weatherForecastDao.clearData(sourceId = currentSourceName, cityId = cityName)
            val dataDTO = getWeatherFromRemote(lat = lat, lon = lon)
            val weatherData = mapper.mapOpenWeatherToWeatherData(dataDTO)
            saveWeatherToLocal(weatherData = weatherData)
        }
        return getWeatherFromLocal(cityName = currentCity)
    }


    private suspend fun getWeatherFromRemote(lat: Float, lon: Float) = service.getForecastByCoorddinates(
        latitude = lat.toString(),
        longitude = lon.toString(),
        appId = OPEN_WEATHER_API_KEY,
        units = "metric",
        lang = "ru",
//            nDays = 5
    )


    private suspend fun getWeatherFromLocal(cityName: String): WeatherData {
        var weatherData: WeatherData? = null
        val weatherList =
            weatherForecastDao.getWeatherList(
                cityName = currentCity,
                sourceId = currentSourceName
            )
        Log.e("ERROR", "$cityName сделали запрос в локальную БД")
        if (weatherList.size > 0) {
            Log.e("ERROR", "WeatherData is full")
            weatherData = mapper.mapForecastDbModelListToWeatherData(weatherList)
        } else {
            weatherData =
                WeatherData(cityName = currentCity, cityLongitude = 0f, cityLatitude = 0f)
            Log.e("ERROR", "$currentCity WeatherData is EMPTY")
        }
        return weatherData
    }

    private suspend fun saveWeatherToLocal(weatherData: WeatherData) {
        val df = DecimalFormat("##.0")
        val latitude2 = weatherData.cityLatitude
        val longitude2 = weatherData.cityLongitude
        val weatherList = mutableListOf<ForecastDbModel>()
        val cityName = weatherData.cityName
        if (weatherData.forecastList.isNotEmpty()) {
            weatherForecastDao.clearData(
                sourceId = currentSourceName,
//                cityId = currentCity
            )
            Log.e("ERROR", "OpenWeather затерли $cityName")
        }
        weatherData.forecastList.forEach {
            val model = ForecastDbModel(
                id = 0,
                idCity = currentCity,
                idSource = currentSourceName,
                latitude = latitude2,
                longitude = longitude2,
                timeStamp = it.timeStamp,
                temperature = (it.temperatureMax + it.temperatureMin) / 2,
                temperatureFeelsLike = (it.temperatureFeelsLikeMax + it.temperatureFeelsLikeMin) / 2,
                humidity = it.humidity,
                weatherCondition = it.condition,
                weatherConditionIconId = it.conditionIconId,
                cityKladr = newCityKladr
            )
            weatherList.add(model)
        }
        weatherForecastDao.addForecastList(weatherList)

    }

    private fun needToUpdate(): Boolean {
        return true
    }

    override suspend fun getCityByName(cityName: String): List<GeocodingDTO> {
        return service.getCoordByName(cityName, appId = OPEN_WEATHER_API_KEY)

    }
}

