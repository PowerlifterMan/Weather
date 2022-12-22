package com.example.weather.domain


data class SimpleLocation(
    val name: String = "Москва",
    val lontitude: String = "",
    val latitude: String = ""
)

data class WindInfo(
    val description: String = "безветренно",
    val speed: Float = 0f,
    val speedMax: Float = 0f
)

data class DayData(
    val id: Int,
    val measurementSystem: Int,
    val location: SimpleLocation,
    val dayTemp: Float = 20f,
    val nightTemp: Float = 20f,

    val weatherDescription: String = "Good",
    val windInfo: WindInfo
)
companion object {}
const val METRIC_SYSTEM = 1
const val IMPERIAL_SYSTEM = 2