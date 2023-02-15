package com.example.weather.domain

class CityForecastData(
    var city : CurrentCity,
    val forecastList: List<TempOnTime>

)

class TempOnTime(
    val timestamp: Long,
    val temp: Float,
    val tempFeelsLike: Float
)
