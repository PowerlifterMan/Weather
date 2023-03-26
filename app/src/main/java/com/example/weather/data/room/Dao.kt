package com.example.weather.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


@Dao
interface WeatherForecastDao {
    //    @Query("SELECT * FROM forecast WHERE idSource=:sourceId")
    @Query("SELECT * FROM forecast WHERE idSource=:sourceId AND latitude=:lat AND longitude=:lon")
    fun getWeatherList(
        sourceId: String,
        lat: Float,
        lon: Float,
    ): List<ForecastDbModel>

    @Query("SELECT * FROM forecast WHERE idCity=:cityName")
    fun getWeatherList(
        cityName: String,
    ): List<ForecastDbModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addForecastItem(forecastDbModel: ForecastDbModel)

    @Query("DELETE FROM forecast WHERE idSource=:sourceId")
    fun clearData(sourceId: String)

}

@Dao
interface ForecastSourceDao {
    @Query("SELECT * FROM source")
    fun getSourceList()
}