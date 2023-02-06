package com.example.weather.domain


const val DEFAULT_DESCRIPTION = "none"

const val DEFAULT_TEMPERATURE = 20f
const val DEFAULT_LOCATION_NAME = "Москва"
const val DEFAULT_LATITUDE = "55.7522"
const val DEFAULT_LONGITUDE = "37.6156"

data class CurrentCity(
    val name: String = DEFAULT_LOCATION_NAME,
    val longitude: String = DEFAULT_LONGITUDE,
    val latitude: String = DEFAULT_LATITUDE,

)
data class CurrentLocation(
    val city: CurrentCity,
    val forecast: List<DayData>
)


data class DayData(
    val dayTemp: Float = DEFAULT_TEMPERATURE,
    val nightTemp: Float = DEFAULT_TEMPERATURE,
    val weatherDescription: String = DEFAULT_DESCRIPTION,
    val windInfo: WindInfo,
    val forecastOnHours : List<ForecastOnHour>,
    val rainFall: String,

)

data class ForecastOnHour(
    val temperature: Float = DEFAULT_TEMPERATURE,
    val weatherDescription: String = DEFAULT_DESCRIPTION,
    val windInfo: WindInfo,
    val rainFall: String,

)

data class WindInfo(
    val description: String = DEFAULT_DESCRIPTION,
    val speed: Float = 0f,
    val speedMax: Float = 0f,
    val windDirection: String = DEFAULT_DESCRIPTION
)
data class RecyclerViewItem(
    var dayNumber: String = "",
    val temperature: String = DEFAULT_TEMPERATURE.toString(),
    val description: String = DEFAULT_DESCRIPTION

)
