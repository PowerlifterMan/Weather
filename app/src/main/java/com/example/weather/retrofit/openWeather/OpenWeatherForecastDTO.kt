package com.example.weather.retrofit.openWeather

import com.example.weather.domain.CurrentCity
import com.google.gson.annotations.SerializedName

class OpenWeatherForecastDTO(
    @SerializedName("city")
    val city: City,
    @SerializedName("cnt")
    val numberDays: City,
    @SerializedName("list")
    val weatherDataList: List<WeatherData>,

)

class WeatherData (
    @SerializedName("dt")
    val timeWeatherData: Long,
    @SerializedName("dt")
    val tempOnCurrentTime: DayTemperature
        )

class DayTemperature (
    @SerializedName("day")
    val tempOnDay: Float,
    @SerializedName("night")
    val tempOnNight: Float
)

class City(
    @SerializedName("name")
    val cityName: String,
    @SerializedName("coord")
    val cityCoord: Coordinates

)

class Coordinates (
    @SerializedName("lon")
    val coordLongitude: Float,
    @SerializedName("lat")
    val coordLatitude: Float
)
