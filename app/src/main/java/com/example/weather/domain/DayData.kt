package com.example.weather.domain


const val DEFAULT_DESCRIPTION = "none"

const val DEFAULT_TEMPERATURE = 20f
const val DEFAULT_LOCATION_NAME = "Москва"
const val DEFAULT_COUNTRY = "RU"
const val DEFAULT_LATITUDE = "55.7522"
const val DEFAULT_LONGITUDE = "37.6156"

data class CurrentCity(
    val name: String = DEFAULT_LOCATION_NAME,
    val longitude: String = DEFAULT_LONGITUDE,
    val latitude: String = DEFAULT_LATITUDE,
    val country: String = DEFAULT_COUNTRY

    )

data class RecyclerViewItem(
    var dayNumber: String = "",
    val temperature: String = DEFAULT_TEMPERATURE.toString(),
    val description: String = DEFAULT_DESCRIPTION

    )
