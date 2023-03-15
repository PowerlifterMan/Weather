package com.example.weather.domain

data class WeatherData(
    val cityName: String,
    val cityLongitude: Float,
    val cityLatitude: Float,
    val currentTemp: CurrentTemp,
    val forecastList: List<CurrentTemp>
)

data class CurrentTemp(
    val timeStamp: Long,
    val temperatureMin: Float,
    val temperatureMax: Float,
    val temperatureFeelsLikeMin: Float,
    val temperatureFeelsLikeMax: Float,
    val humidity: Int
)
