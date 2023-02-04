package com.example.weather.retrofit

import com.google.gson.annotations.SerializedName

class OpenWeatherDto (
    @SerializedName("coord")
    val point: LocationDto,
    @SerializedName("weather")
    val currentWeather: List<WeatherDto>
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