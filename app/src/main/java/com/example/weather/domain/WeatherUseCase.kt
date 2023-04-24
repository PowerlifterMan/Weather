package com.example.weather.domain

import android.os.Build
import com.example.weather.data.*
import com.example.weather.di.NinjaRepo
import com.example.weather.di.OpenMeteoRepo
import com.example.weather.di.OpenWeatherRepo
import com.example.weather.presentation.main.DEFAULT_SOURCE_NAME
import com.example.weather.presentation.main.SOURCE_NINJAS
import com.example.weather.presentation.main.SOURCE_OPEN_METEO
import com.example.weather.presentation.main.SOURCE_OPEN_WEATHER
import io.reactivex.rxjava3.core.Single
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.ZoneId
import java.util.*
import javax.inject.Inject

class WeatherUseCase(
    private val ninjaRepo: WeatherRepository,
    private val openMeteoRepo: WeatherRepository,
    private val openWeatherRepo: WeatherRepository,
) {
    fun getForecast(
        lat: Float = DEFAULT_LATITUDE,
        lon: Float = DEFAULT_LONGITUDE,
        sourceName: String = DEFAULT_SOURCE_NAME,
        city: String
    ): Single<WeatherData> {
        val currentRepo: WeatherRepository = when (sourceName) {
            SOURCE_OPEN_METEO -> {
                openMeteoRepo
            }

            SOURCE_OPEN_WEATHER -> {
                openWeatherRepo
            }

            SOURCE_NINJAS -> {
                openMeteoRepo
            }

            else -> openWeatherRepo
        }
        return currentRepo.getWeather(lat = lat, lon = lon, cityName = city)
    }

    fun getForecast(
        lat: Float = DEFAULT_LATITUDE,
        lon: Float = DEFAULT_LONGITUDE,
        sourceNameList: List<String> = listOf(),
        city: String
    ): Single<WeatherData> {
        val d = LinkedList<String>()
        val sources = mutableListOf<Single<WeatherData>>()
        if (sourceNameList.isNotEmpty()) {
            sourceNameList.forEach { sourceName ->
                val currentRepo: WeatherRepository = when (sourceName) {
                    SOURCE_OPEN_METEO -> {
                        openMeteoRepo
                    }

                    SOURCE_OPEN_WEATHER -> {
                        openWeatherRepo
                    }

                    SOURCE_NINJAS -> {
                        ninjaRepo
                    }

                    else -> openWeatherRepo
                }
                sources.add(
                    currentRepo.getWeather(lat = lat, lon = lon, cityName = city)
                )
            }

        }
        return Single.zip(sources) { dataArray ->
            var resultWeatherData = dataArray[0] as WeatherData
            dataArray.forEachIndexed() { index, value ->
                resultWeatherData =
                    combineWeatherData(summaryData = resultWeatherData, data = value as WeatherData)
            }
            resultWeatherData
        }

    }

    fun getCityDto(city: String): Single<List<GeocodingDTO>> {
        return openWeatherRepo.getCityByName(city)
    }

    companion object {
        const val DEFAULT_LONGITUDE = 42.86f
        const val DEFAULT_CITY = "Yessentuki"
        const val DEFAULT_LATITUDE = 44.044f
        const val SECONDS_IN_DAY = 86400
        const val SECONDS_IN_HOUR = 3600
    }

    fun combineWeatherData(summaryData: WeatherData, data: WeatherData): WeatherData {
        val currentDate = SimpleDateFormat("dd/M/yyyy").format(Date())
        summaryData.cityLatitude = data.cityLatitude
        summaryData.cityLongitude = data.cityLongitude
        summaryData.cityName = data.cityName
        summaryData.currentTemp = combineCurrentTemp(summaryData.currentTemp, data.currentTemp)
        val list = data.forecastList
        val oldList = summaryData.forecastList.toList()
        summaryData.forecastList = combineListCurrentTemp(oldList, data.forecastList)
        //val period = Period(0,0,1)
        val calendar = Calendar.getInstance()
        val summaryList = summaryData.forecastList
        return summaryData
    }

    private fun combineListCurrentTemp(
        averageForecastList: List<CurrentTemp>,
        forecastList: List<CurrentTemp>
    ): List<CurrentTemp> {
        return averageForecastList.mapIndexed { index, currentTemp ->
            combineCurrentTemp(
                currentTemp,
                forecastList[index]
            )
        }
    }

    private fun combineCurrentTemp(
        averageCurrentTemp: CurrentTemp,
        currentTemp: CurrentTemp
    ): CurrentTemp {
//        val tempMin = currentTemp.temperatureMin
        return CurrentTemp(
            timeStamp = currentTemp.timeStamp,
            temperatureMin = (averageCurrentTemp.temperatureMin + currentTemp.temperatureMin) / 2,
            temperatureMax = (averageCurrentTemp.temperatureMax + currentTemp.temperatureMax) / 2,
            temperatureFeelsLikeMin = (averageCurrentTemp.temperatureFeelsLikeMin + currentTemp.temperatureFeelsLikeMin) / 2,
            temperatureFeelsLikeMax = (averageCurrentTemp.temperatureFeelsLikeMax + currentTemp.temperatureFeelsLikeMax) / 2,
            humidity = currentTemp.humidity,
            condition = currentTemp.condition ?: averageCurrentTemp.condition,
            conditionIconId = currentTemp.conditionIconId ?: averageCurrentTemp.conditionIconId
        )

    }
}
