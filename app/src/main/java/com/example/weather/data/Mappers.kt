package com.example.weather.data

import com.example.weather.domain.*
import com.example.weather.retrofit.OpenWeatherDto
import com.example.weather.retrofit.daData.CityListItem
import com.example.weather.retrofit.daData.Suggestions
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
                latitude = openWeatherForecastDTO.city.cityCoord.coordLatitude.toString(),
            ),
            forecastList = mapToListTempOnTime(openWeatherForecastDTO.list)
        )

    fun mapDayForecastToTempOnTime(dayForecast: DayForecast) = TempOnTime(
        timestamp = dayForecast.dateOfForecast,
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

    fun mapOpenWeatherToCurrentWeather(openWeatherDto: OpenWeatherDto) = CityForecastData(
        city = CurrentCity(
            name = openWeatherDto.cityName,
            longitude = openWeatherDto.point.longitudeDto.toString(),
            latitude = openWeatherDto.point.latitudeDto.toString(),
        ),
        forecastList = listOf(
            TempOnTime(
                timestamp = Date().time,
                temp = openWeatherDto.mainWeather.currentTemp,
                tempFeelsLike = openWeatherDto.mainWeather.currentTempFeels
            )
        )
    )

    fun mapSuggestionsToCityListItem(suggestions: Suggestions): List<CityListItem> =
        suggestions.suggestions.map { item ->
            CityListItem(
                unrestrictedAddres = item.unrestrictedValue,
                country = item.data.country.toString(),
                regionWithType = item.data.regionWithType ?: "",
                lontitude = item.data.lontitude ?: "",
                latitude = item.data.lantitude ?: ""

            )
        }

    fun mapOpenForecastToCWeatherData(dTO: OpenWeatherForecastDTO) =
        WeatherData(
            cityName = dTO.city.cityName,
            cityLatitude = dTO.city.cityCoord.coordLatitude,
            cityLongitude = dTO.city.cityCoord.coordLongitude,
            currentTemp = mapDayForecastToCurrentTemp(dTO.list[0]),
            forecastList = mapToListCurrentTemp(dTO.list)
        )

    private fun mapToListCurrentTemp(list: List<DayForecast>): List<CurrentTemp> {
        return list.map {mapDayForecastToCurrentTemp(it)}
    }

    fun mapDayForecastToCurrentTemp(dayForecast: DayForecast) = CurrentTemp(
        timeStamp = dayForecast.dateOfForecast,
        temperatureMin = dayForecast.mainForecastData.temp,
        temperatureMax = dayForecast.mainForecastData.temp,
        temperatureFeelsLikeMax = dayForecast.mainForecastData.tempFeels,
        temperatureFeelsLikeMin = dayForecast.mainForecastData.tempFeels,
        humidity = dayForecast.mainForecastData.humidity

    )


}