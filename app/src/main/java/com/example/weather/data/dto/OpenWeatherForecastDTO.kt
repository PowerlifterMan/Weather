package com.example.weather.retrofit.openWeather

import com.google.gson.annotations.SerializedName

class OpenWeatherForecastDTO(
    @SerializedName("cod")
    val cod: String,
    @SerializedName("message")
    val message: Int,
    @SerializedName("cnt")
    val numberDays: Int,
    @SerializedName("list")
    val list: List<DayForecast>,
    @SerializedName("city")
    val city: City
)

class DayForecast(
    @SerializedName("dt")
    val dateOfForecast: Long,
    @SerializedName("main")
    val mainForecastData: MainForecastData,
//    @SerializedName("")
//    val
)

class MainForecastData(
    @SerializedName("temp")
    val temp: Float,
    @SerializedName("feels_like")
    val tempFeels: Float,
    @SerializedName("humidity")
    val humidity: Int
)

class DayTemperature(
    @SerializedName("day")
    val tempOnDay: Float,
    @SerializedName("night")
    val tempOnNight: Float
)

class City(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val cityName: String,
    @SerializedName("coord")
    val cityCoord: Coordinates

)

class Coordinates(
    @SerializedName("lon")
    val coordLongitude: Float,
    @SerializedName("lat")
    val coordLatitude: Float
)
