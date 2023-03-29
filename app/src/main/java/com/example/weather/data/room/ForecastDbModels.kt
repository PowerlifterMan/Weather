package com.example.weather.data.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "forecast")
class ForecastDbModel(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int,

    @ColumnInfo(name = "idCity")
    val idCity: String,

    @ColumnInfo(name = "idSource")
    val idSource: String,

    @ColumnInfo(name = "latitude")
    val latitude: Float?,

    @ColumnInfo(name = "longitude")
    val longitude: Float?,

    @ColumnInfo(name = "timeStamp")
    val timeStamp: Long,

    @ColumnInfo(name = "temperature")
    val temperature: Float,

    @ColumnInfo(name = "temperatureFeelsLike")
    val temperatureFeelsLike: Float,

    @ColumnInfo(name = "humidity")
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