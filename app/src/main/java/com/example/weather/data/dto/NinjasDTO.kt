package com.example.weather.data.dto

import com.google.gson.annotations.SerializedName

class NinjasDTO (
    @SerializedName("cloud_pct")
    val cloud: Int,
    @SerializedName("temp")
    val temperature: Float,
    @SerializedName("feels_like")
    val temperatureFeelsLike: Float,
    @SerializedName("humidity")
    val humidity: Int,
    @SerializedName("min_temp")
    val minTemperature: Float,
    @SerializedName("max_temp")
    val maxTemperature: Float,
    @SerializedName("wind_speed")
    val windSpeed: Float,
    @SerializedName("wind_degrees")
    val windDegrees: Int,
    @SerializedName("sunrise")
    val sunrise: Long,
    @SerializedName("sunset")
    val sunset: Long,

)