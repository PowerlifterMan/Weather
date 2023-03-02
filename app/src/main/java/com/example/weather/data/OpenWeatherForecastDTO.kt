package com.example.weather.retrofit.openWeather

import com.example.weather.domain.CurrentCity
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

class WeatherData(
    @SerializedName("dt")
    val timeWeatherData: Long,
    @SerializedName("temp")
    val tempOnCurrentTime: DayTemperature
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

class GeocodingDTO(
    @SerializedName("name")
    val searchName: String,
    @SerializedName("local_names")
    val local_names: LocalNamesDTO,
    @SerializedName("lat")
    val lat: String,
    @SerializedName("lon")
    val lon: String,
    @SerializedName("country")
    val country: String,
    @SerializedName("state")
    val state: String

    )

class LocalNamesDTO(
    @SerializedName("en")
    val language_en: String,
    @SerializedName("ru")
    val language_ru: String,
    @SerializedName("ascii")
    val language_ascii: String

)
