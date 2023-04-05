package com.example.weather.domain

import android.os.Build
import androidx.room.util.copy
import com.example.weather.data.*
import com.example.weather.presentation.main.DEFAULT_SOURCE_NAME
import com.example.weather.presentation.main.SOURCE_NINJAS
import com.example.weather.presentation.main.SOURCE_OPEN_METEO
import com.example.weather.presentation.main.SOURCE_OPEN_WEATHER
import io.reactivex.rxjava3.core.Single
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.ZoneId
import java.util.*

class WeatherUseCase() {
    val weatherDataList = mutableListOf<WeatherData>()
    val startDayTime = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        LocalDate.now().atStartOfDay(ZoneId.systemDefault())
    } else {
        TODO("VERSION.SDK_INT < O")
    }

    fun getForecast(
        lat: Float = DEFAULT_LATITUDE,
        lon: Float = DEFAULT_LONGITUDE,
        sourceName: String = DEFAULT_SOURCE_NAME,
        city: String
    ): Single<WeatherData> {
        val currentRepo: WeatherRepository = when (sourceName) {
            SOURCE_OPEN_METEO -> {
                OpenMeteoRepositoryImpl
            }
            SOURCE_OPEN_WEATHER -> {
                OpenWeatheRepositoryImpl
            }
            SOURCE_NINJAS -> {
                NinjasRepositoryImpl
            }
            else -> OpenWeatheRepositoryImpl
        }
        return currentRepo.getWeather(lat = lat, lon = lon, cityName = city)
    }

    fun getForecast(
        lat: Float = DEFAULT_LATITUDE,
        lon: Float = DEFAULT_LONGITUDE,
        sourceNameList: List<String> = listOf(),
        city: String
    ): Single<WeatherData> {
        val sources = mutableListOf<Single<WeatherData>>()
        if (sourceNameList.isNotEmpty()) {
            sourceNameList.forEach { sourceName ->
                val currentRepo: WeatherRepository = when (sourceName) {
                    SOURCE_OPEN_METEO -> {
                        OpenMeteoRepositoryImpl
                    }
                    SOURCE_OPEN_WEATHER -> {
                        OpenWeatheRepositoryImpl
                    }
                    SOURCE_NINJAS -> {
                        NinjasRepositoryImpl
                    }
                    else -> OpenWeatheRepositoryImpl
                }
                sources.add(
                    currentRepo.getWeather(lat = lat, lon = lon, cityName = city)
                )
            }

        }
        return Single.zip(sources, { dataArray ->
            var resultWeatherData = WeatherData()
            dataArray.forEachIndexed() { index, value ->
                resultWeatherData =
                    combineWeatherData(summaryData = resultWeatherData, data = value as WeatherData)
            }
            resultWeatherData
        })

    }

    fun getCityDto(city: String): Single<List<GeocodingDTO>> {
        return OpenWeatheRepositoryImpl.getCityByName(city)
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
        val oldList = summaryData.forecastList`
        summaryData.forecastList = combineListCurrentTemp(summaryData.forecastList.copy(), data.forecastList)
        //val period = Period(0,0,1)
        val calendar = Calendar.getInstance()
        val summaryList = summaryData.forecastList
        return summaryData
    }

    private fun combineListCurrentTemp(averageForecastList: List<CurrentTemp>, forecastList: List<CurrentTemp>): List<CurrentTemp> {
        return averageForecastList.mapIndexed { index, currentTemp ->  combineCurrentTemp(currentTemp, forecastList[index])}
    }

    private fun combineCurrentTemp(averageCurrentTemp: CurrentTemp, currentTemp: CurrentTemp): CurrentTemp {
//        val tempMin = currentTemp.temperatureMin
        return CurrentTemp(timeStamp = currentTemp.timeStamp,
            temperatureMin = (averageCurrentTemp.temperatureMin + currentTemp.temperatureMin)/2,
            temperatureMax = (averageCurrentTemp.temperatureMax + currentTemp.temperatureMax)/2,
            temperatureFeelsLikeMin = (averageCurrentTemp.temperatureFeelsLikeMin + currentTemp.temperatureFeelsLikeMin)/2,
            temperatureFeelsLikeMax = (averageCurrentTemp.temperatureFeelsLikeMax + currentTemp.temperatureFeelsLikeMax)/2,
        humidity = currentTemp.humidity
        )

    }
}
