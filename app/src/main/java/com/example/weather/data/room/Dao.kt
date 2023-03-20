package com.example.weather.data.room

import androidx.room.Dao
import androidx.room.Query


@Dao
interface WeatherForecastDao {
    @Query
}

@Dao
interface ForecastSourceDao {

}