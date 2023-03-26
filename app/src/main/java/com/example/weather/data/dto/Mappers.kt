package com.example.weather.data.dto

import com.example.weather.OpenMeteo.DailyDTO
import com.example.weather.OpenMeteo.OpenMeteoCurrentWeatherDTO
import com.example.weather.OpenMeteo.OpenMeteoDTO
import com.example.weather.data.room.ForecastDbModel
import com.example.weather.domain.*
import com.example.weather.retrofit.openWeather.DayForecast
import com.example.weather.retrofit.openWeather.OpenWeatherForecastDTO
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.*

class Mappers {


    fun mapForecastDbModelListToWeatherData(list: List<ForecastDbModel>): WeatherData {

        return WeatherData(
            cityName = "",
            cityLatitude = list[0].latitude,
            cityLongitude = list[0].longitude,
            currentTemp = mapDbModelToCurrentTemp(list[0]),
            forecastList = list.map { item -> mapDbModelToCurrentTemp(item) })

    }

    fun mapDbModelToCurrentTemp(forecastDbModel: ForecastDbModel): CurrentTemp {
        return CurrentTemp(
            timeStamp = forecastDbModel.timeStamp,
            temperatureMin = forecastDbModel.temperature,
            temperatureMax = forecastDbModel.temperature,
            temperatureFeelsLikeMax = forecastDbModel.temperatureFeelsLike,
            temperatureFeelsLikeMin = forecastDbModel.temperatureFeelsLike,
            humidity = forecastDbModel.humidity
        )
    }

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

    fun mapNinjasDtoToWeatherData(dto: NinjasDTO): WeatherData {
        val returned = WeatherData(
            cityName = DEFAULT_NAME,
            cityLatitude = DEFAULT_FLOAT_VALUE,
            cityLongitude = DEFAULT_FLOAT_VALUE,
            currentTemp = CurrentTemp(
                timeStamp = dto.sunrise,
                temperatureMin = dto.minTemperature,
                temperatureMax = dto.maxTemperature,
                temperatureFeelsLikeMin = dto.temperatureFeelsLike,
                temperatureFeelsLikeMax = dto.temperatureFeelsLike,
                humidity = dto.humidity
            ),
            forecastList = listOf()
        )
        return returned
    }

    fun mapOpenMeteoToWeatherData(dto: OpenMeteoDTO): WeatherData =
        WeatherData(
            cityName = "",
            cityLatitude = dto.latitude,
            cityLongitude = dto.longitude,
            currentTemp = CurrentTemp(
                timeStamp = dto.currentWeather.currentTime.toLong(),
                temperatureMax = dto.currentWeather.temperature,
                temperatureMin = dto.currentWeather.temperature,
                temperatureFeelsLikeMin = dto.currentWeather.temperature,
                temperatureFeelsLikeMax = dto.currentWeather.temperature,
                humidity = 0
            ),
            forecastList = mapDaylyDTOCurrentTemp(dto.daily)

        )


    fun mapOpenForecastToWeatherData(dTO: OpenWeatherForecastDTO) =
        WeatherData(
            cityName = dTO.city.cityName,
            cityLatitude = dTO.city.cityCoord.coordLatitude,
            cityLongitude = dTO.city.cityCoord.coordLongitude,
            currentTemp = mapDayForecastToCurrentTemp(dTO.list[0]),
            forecastList = mapToListCurrentTemp(dTO.list)
        )


    private fun mapToListCurrentTemp(list: List<DayForecast>): List<CurrentTemp> {
        return list.map { mapDayForecastToCurrentTemp(it) }
    }


    private fun mapDaylyDTOCurrentTemp(openMeteoDto: DailyDTO): List<CurrentTemp> {
        val listTemperature = openMeteoDto.temperature_2m
        val listApparentTemperature = openMeteoDto.apparent_temperature_2m
        val listTime = openMeteoDto.time
        val formatter = SimpleDateFormat("yyyy-mm-dd")
        val outputList = mutableListOf<CurrentTemp>()
        listTime.forEachIndexed { index, item ->
//            val dateString = item.substringBefore("T")
//            val date = formatter.parse(dateString)
            val newItem = CurrentTemp(
                timeStamp = item.toLong(),
//                timeStamp = Timestamp.valueOf(item.substringBefore("T")).time,
                temperatureMin = listTemperature[index],
                temperatureMax = listTemperature[index],
                temperatureFeelsLikeMax = listApparentTemperature[index],
                temperatureFeelsLikeMin = listApparentTemperature[index],
                humidity = 0

            )
            outputList.add(newItem)
        }
        return outputList
    }


    fun mapDayForecastToCurrentTemp(dayForecast: DayForecast) = CurrentTemp(
        timeStamp = dayForecast.dateOfForecast,
        temperatureMin = dayForecast.mainForecastData.temp,
        temperatureMax = dayForecast.mainForecastData.temp,
        temperatureFeelsLikeMax = dayForecast.mainForecastData.tempFeels,
        temperatureFeelsLikeMin = dayForecast.mainForecastData.tempFeels,
        humidity = dayForecast.mainForecastData.humidity

    )

    fun mapDayForecastToCurrentTemp(dayForecast: OpenMeteoCurrentWeatherDTO) = CurrentTemp(
        timeStamp = Timestamp.valueOf(dayForecast.currentTime).time,
        temperatureMin = dayForecast.temperature,
        temperatureMax = dayForecast.temperature,
        temperatureFeelsLikeMax = dayForecast.temperature,
        temperatureFeelsLikeMin = dayForecast.temperature,
        humidity = 0

    )

    companion object {
        const val DEFAULT_NAME = "noname"
        const val DEFAULT_FLOAT_VALUE = -1f
        const val DEFAULT_INT_VALUE = -1
    }
}
