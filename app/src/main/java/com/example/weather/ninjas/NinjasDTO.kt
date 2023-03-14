package com.example.weather.ninjas

import com.google.gson.annotations.SerializedName

class NinjasDTO (
    @SerializedName("cloud_pct")
    val cloud: Int,
    @SerializedName("temp")
    val temperature: Int,
    @SerializedName("feels_like")
    val temperatureFeelsLike: Int,
    @SerializedName("humidity")
    val humidity: Int,
    @SerializedName("min_temp")
    val minTemperature: Int,
    @SerializedName("max_temp")
    val maxTemperature: Int,
    @SerializedName("wind_speed")
    val windSpeed: Float,
    @SerializedName("wind_degrees")
    val windDegrees: Int,
    @SerializedName("sunrise")
    val sunrise: Long,
    @SerializedName("sunset")
    val sunset: Long,

)