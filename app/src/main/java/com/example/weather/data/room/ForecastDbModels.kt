package com.example.weather.data.room

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.weather.retrofit.openWeather.DayTemperature

@Entity(tableName = "forecast")
data class ForecastDbModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val idSource: Int,
    val timeStamp: Long,
    val temperature: Float,
    val temperatureFeelsLike: Float,
    val humidity: Int
)

@Entity(tableName = "settings")
data class ForecastSourceDbModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    var nameSource: String,
    var urlSource: String,
    var endPointSource: String,

)