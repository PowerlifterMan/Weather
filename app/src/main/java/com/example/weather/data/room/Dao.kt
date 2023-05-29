package com.example.weather.data.room

import androidx.room.*


@Dao
interface WeatherForecastDao {
    //    @Query("SELECT * FROM forecast WHERE idSource=:sourceId")
    @Query("SELECT * FROM forecast WHERE idSource=:sourceId AND latitude = :lat AND longitude = :lon")
    suspend fun getWeatherList(
        sourceId: String,
        lat: Float,
        lon: Float,
    ): List<ForecastDbModel>

//    @Query("SELECT * FROM forecast WHERE idCity LIKE :cityName  ORDER BY timeStamp")
    @Query("SELECT * FROM forecast WHERE idCity LIKE :cityName AND idSource LIKE :sourceId ORDER BY timeStamp")
//    @Query("SELECT * FROM forecast WHERE idCity=:cityName AND idSource=:sourceId")
    suspend fun getWeatherList(
        cityName: String,
        sourceId: String
    ): List<ForecastDbModel>

    @Query("SELECT * FROM forecast WHERE idCity LIKE :cityName  ORDER BY timeStamp")
//    @Query("SELECT * FROM forecast WHERE idCity = :cityName AND idSource = :sourceId ORDER BY timeStamp")
//    @Query("SELECT * FROM forecast WHERE idCity=:cityName AND idSource=:sourceId")
    suspend fun getWeatherList(
        cityName: String,
    ): List<ForecastDbModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addForecastItem(forecastDbModel: ForecastDbModel)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addForecastList(forecastDbModelList: List<ForecastDbModel>)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateDB(forecastDbModel: ForecastDbModel)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateDB(forecastDbModel: List<ForecastDbModel>)

//    @Query("DELETE FROM forecast WHERE idSource = :sourceId")
    @Query("DELETE FROM forecast WHERE idSource = :sourceId AND idCity = :cityId")
    suspend fun clearData(
        sourceId: String,
        cityId: String
    )
    @Query("DELETE FROM forecast WHERE idSource = :sourceId ")
    suspend fun clearData(
        sourceId: String,
    )

    @Transaction


    @Query("UPDATE forecast SET idSource = :sourceId")
    suspend fun updateDataSet(sourceId: String): Int

}
