package com.example.weather.domain

data class SimpleLocation(val name: String, val lontitude: String, val latitude: String)

data class DayData(
    val location: SimpleLocation,
    val dayTemp: Float,
    val nightTemp: Float,
    val weatherDescription: String
)
