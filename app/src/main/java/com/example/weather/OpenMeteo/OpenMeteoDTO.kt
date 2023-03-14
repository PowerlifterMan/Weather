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
    @SerializedName("daily")
    val daily: DailyDTO
)

class OpenMeteoCurrentWeatherDTO(
    @SerializedName("time")
    val currentTime: String,
    @SerializedName("temperature")
    val temperature: Float,
    @SerializedName("weathercode")
    val weathercode: Int,
    @SerializedName("windspeed")
    val windspeed: Float,
    @SerializedName("winddirection")
    val winddirection: Int,
)

class HourlyDTO(
    @SerializedName("time")
    val time: List<String>,
    @SerializedName("temperature_2m")
    val temperature_2m: List<Float>
)
class DailyDTO(
    @SerializedName("time")
    val time: List<String>,
    @SerializedName("temperature_2m_max")
    val temperature_2m: List<Float>,
    @SerializedName("apparent_temperature_max")
    val apparent_temperature_2m: List<Float>
)
