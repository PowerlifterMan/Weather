package com.example.weather.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


@Dao
interface WeatherForecastDao {
//    @Query("SELECT * FROM forecast WHERE idSource=:sourceId")
    @Query("ELECT * FROM forecast WHERE idSource=:sourceId")
    fun getWeatherList(sourceId: Int):List<ForecastDbModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addForecastItem(forecastDbModel: ForecastDbModel)

    @Query("DELETE * FROM forecast")
    fun clearData()
}

@Dao
interface ForecastSourceDao {
    @Query("SELECT * FROM source")
    fun getSourceList()
}