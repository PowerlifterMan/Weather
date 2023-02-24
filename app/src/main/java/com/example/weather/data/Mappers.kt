package com.example.weather.data

import com.example.weather.domain.CityForecastData
import com.example.weather.domain.CurrentCity
import com.example.weather.domain.TempOnTime
import com.example.weather.retrofit.openWeather.OpenWeatherForecastDTO
import com.example.weather.retrofit.openWeather.DayForecast
import java.text.SimpleDateFormat
import java.util.*

class Mappers {
    fun mapOpenForecastToCityForecast(openWeatherForecastDTO: OpenWeatherForecastDTO) =
        CityForecastData(
            city = CurrentCity(
                name = openWeatherForecastDTO.city.cityName,
                longitude = openWeatherForecastDTO.city.cityCoord.coordLongitude.toString(),
                latitude =  openWeatherForecastDTO.city.cityCoord.coordLatitude.toString(),
            ),
            forecastList = mapToListTempOnTime(openWeatherForecastDTO.list)
        )

    fun mapDayForecastToTempOnTime(dayForecast: DayForecast) = TempOnTime(timestamp = dayForecast.dateOfForecast,
        temp = dayForecast.mainForecastData.temp,
        tempFeelsLike = dayForecast.mainForecastData.tempFeels
    )
    fun mapToListTempOnTime(list: List<DayForecast>) = list.map { mapDayForecastToTempOnTime(it) }
    private fun getDateTime(s: Long): String? {
        try {
            val sdf = SimpleDateFormat("yyyy/MM/dd")
            val netDate = Date(s * 1000)
            return sdf.format(netDate)
        } catch (e: Exception) {
            return e.toString()
        }
    }

}