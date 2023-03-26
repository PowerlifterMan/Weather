package com.example.weather.data.room

import androidx.room.*


@Dao
interface WeatherForecastDao {
    //    @Query("SELECT * FROM forecast WHERE idSource=:sourceId")
    @Query("SELECT * FROM forecast WHERE idSource=:sourceId AND latitude = :lat AND longitude = :lon")
    fun getWeatherList(
        sourceId: String,
        lat: Float,
        lon: Float,
    ): List<ForecastDbModel>

    @Query("SELECT * FROM forecast WHERE idCity = :cityName AND idSource = :sourceId")
//    @Query("SELECT * FROM forecast WHERE idCity=:cityName AND idSource=:sourceId")
    fun getWeatherList(
        cityName: String,
        sourceId:String
    ): List<ForecastDbModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addForecastItem(forecastDbModel: ForecastDbModel)

    @Query("DELETE FROM forecast WHERE idSource = :sourceId")
    fun clearData(sourceId: String)

}

@Transaction
fun adsadasd(){

}

@Dao
interface ForecastSourceDao {
    @Query("SELECT * FROM source")
    fun getSourceList()
}