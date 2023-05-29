package com.example.weather.domain

import androidx.lifecycle.LiveData
import com.example.weather.data.GeocodingDTO
import com.example.weather.data.WeatherRepository
import com.example.weather.presentation.main.DEFAULT_SOURCE_NAME
import com.example.weather.presentation.main.SOURCE_NINJAS
import com.example.weather.presentation.main.SOURCE_OPEN_METEO
import com.example.weather.presentation.main.SOURCE_OPEN_WEATHER
import io.reactivex.rxjava3.core.Single

class WeatherUseCase(
    private val ninjaRepo: WeatherRepository,
    private val openMeteoRepo: WeatherRepository,
    private val openWeatherRepo: WeatherRepository,
) {
    suspend fun getForecast(
        lat: Float = DEFAULT_LATITUDE,
        lon: Float = DEFAULT_LONGITUDE,
        sourceNameList: List<String> = listOf(),
        city: String,
        cityKladr: String
    ): WeatherData {
        val sources = mutableListOf<WeatherData>()
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
                    currentRepo.getWeather(
                        lat = lat,
                        lon = lon,
                        cityName = city,
                        cityKladr = cityKladr
                    )
                )
            }
        }
        var resultWeatherData = sources[0] as WeatherData
        if (sources.size > 1) {
            sources.forEachIndexed { index, value ->
                resultWeatherData =
                    combineWeatherData(
                        summaryData = resultWeatherData,
                        data = value as WeatherData
                    )
            }
        }
        return resultWeatherData
    }

    suspend fun getCityDto(city: String): List<GeocodingDTO> {
        return openWeatherRepo.getCityByName(city)
    }

    companion object {
        const val DEFAULT_LONGITUDE = 42.86f
        const val DEFAULT_LATITUDE = 44.044f
    }

    fun combineWeatherData(summaryData: WeatherData, data: WeatherData): WeatherData {
        summaryData.cityLatitude = data.cityLatitude
        summaryData.cityLongitude = data.cityLongitude
        summaryData.cityName = data.cityName
        summaryData.currentTemp = combineCurrentTemp(summaryData.currentTemp, data.currentTemp)
        val oldList = summaryData.forecastList.toList()
        summaryData.forecastList = combineListCurrentTemp(oldList, data.forecastList)
        //val period = Period(0,0,1)
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
