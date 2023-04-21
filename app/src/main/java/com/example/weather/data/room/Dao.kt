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

    @Query("SELECT * FROM forecast WHERE idCity = :cityName  ORDER BY timeStamp")
//    @Query("SELECT * FROM forecast WHERE idCity = :cityName AND idSource = :sourceId ORDER BY timeStamp")
//    @Query("SELECT * FROM forecast WHERE idCity=:cityName AND idSource=:sourceId")
    fun getWeatherList(
        cityName: String,
        sourceId: String
    ): List<ForecastDbModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addForecastItem(forecastDbModel: ForecastDbModel)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addForecastList(forecastDbModelList: List<ForecastDbModel>)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateDB(forecastDbModel: ForecastDbModel)

    @Query("DELETE FROM forecast WHERE idSource = :sourceId")
//    @Query("DELETE FROM forecast WHERE idSource = :sourceId AND idCity = :cityId")
    fun clearData(
        sourceId: String,
//        cityId: String
    )

    @Transaction


    @Query("UPDATE forecast SET idSource = :sourceId")
    fun updateDataSet(sourceId: String): Int

}


@Dao
interface ForecastSourceDao {
    @Query("SELECT * FROM source")
    fun getSourceList()
}