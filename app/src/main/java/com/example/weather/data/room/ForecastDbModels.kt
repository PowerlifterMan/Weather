package com.example.weather.data.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "forecast")
class ForecastDbModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val idCity: String,
    val idSource: String,
    val latitude: Float?,
    val longitude: Float?,
    val timeStamp: Long,
    val temperature: Float,
    val temperatureFeelsLike: Float,
    val humidity: Int
)

@Entity(tableName = "settings")
class ForecastSettingDbModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    var nameSource: String,
    var urlSource: String,
    var endPointSource: String,

    )