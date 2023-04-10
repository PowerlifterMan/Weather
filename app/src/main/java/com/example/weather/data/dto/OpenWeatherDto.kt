package com.example.weather.retrofit

import com.example.weather.domain.CurrentCity
import com.google.gson.annotations.SerializedName

class OpenWeatherDto (
    @SerializedName("coord")
    val point: LocationDto,
    @SerializedName("weather")
    val currentWeather: List<WeatherDto>,
    @SerializedName("main")
    val mainWeather: mainWeatherData,
    @SerializedName("visibility")
    val visibility: Int,
    @SerializedName("name")
    val cityName: String
        )

class LocationDto(
    @SerializedName("lon")
    val longitudeDto: Float,
    @SerializedName("lat")
    val latitudeDto: Float
)
class WeatherDto(
    @SerializedName("id")
    val id: Int,
    @SerializedName("main")
    val firstDescriptionDto: String,
    @SerializedName("description")
    val secondDescriptionDto: String,
    @SerializedName("icon")
    val iconDto: String
)

class mainWeatherData (
    @SerializedName("temp")
    val currentTemp: Float,
    @SerializedName("feels_like")
    val  currentTempFeels: Float   ,
    @SerializedName("pressure")
    val  currentPressure: Int
)
///******************************************

