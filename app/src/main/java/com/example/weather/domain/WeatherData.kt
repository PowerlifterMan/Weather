package com.example.weather.domain

data class WeatherData(
    val cityName: String = "",
    val cityLongitude: Float,
    val cityLatitude: Float,
    val currentTemp: CurrentTemp = CurrentTemp(),
    val forecastList: List<CurrentTemp> = listOf()
)

data class CurrentTemp(
    val timeStamp: Long = System.currentTimeMillis(),
    val temperatureMin: Float = 0f,
    val temperatureMax: Float = 0f,
    val temperatureFeelsLikeMin: Float = 0f,
    val temperatureFeelsLikeMax: Float = 0f,
    val humidity: Int = 0
)
