package com.example.weather.domain

data class WeatherData(
    var cityName: String = "",
    var cityLongitude: Float? = 0f,
    var cityLatitude: Float? = 0f,
    var currentTemp: CurrentTemp = CurrentTemp(),
    var forecastList: List<CurrentTemp> = listOf()
)

data class CurrentTemp(
    val timeStamp: Long = System.currentTimeMillis(),
    val temperatureMin: Float = 0f,
    val temperatureMax: Float = 0f,
    val temperatureFeelsLikeMin: Float = 0f,
    val temperatureFeelsLikeMax: Float = 0f,
    val humidity: Int = 0
)
