package com.example.weather.OpenMeteo

import com.google.gson.annotations.SerializedName

class OpenMeteoDTO(
    @SerializedName("latitude")
    val latitude: Float,
    @SerializedName("longitude")
    val longitude: Float,
    @SerializedName("timezone")
    val timezone: String,
    @SerializedName("hourly")
    val hourly: HourlyDTO,
    @SerializedName("current_weather")
    val currentWeather: OpenMeteoCurrentWeatherDTO,
)

class OpenMeteoCurrentWeatherDTO(
    @SerializedName("time")
    val currentTime: String,
    @SerializedName("temperature")
    val temperature: Float,
    @SerializedName("weathercode")
    val weathercode: Int,
    @SerializedName("windspeed")
    val windspeed: Int,
    @SerializedName("winddirection")
    val winddirection: Int,
)

class HourlyDTO(
    @SerializedName("time")
    val time: List<String>,
    @SerializedName("temperature_2m")
    val temperature_2m: List<Float>
)
