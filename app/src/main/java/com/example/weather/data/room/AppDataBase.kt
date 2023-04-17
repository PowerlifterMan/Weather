package com.example.weather.data.room

import android.app.Application
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.weather.WeatherApp
import javax.inject.Inject

@Database(
    entities = [ForecastDbModel::class],
    version = 6,
    exportSchema = false
)
abstract class AppDataBase (): RoomDatabase() {

    abstract fun weatherForecastDao(): WeatherForecastDao

//    companion object {
//        @Volatile
//        private var INSTANCE: AppDataBase? = null
//        private val LOCK = Any()
//        private const val DB_NAME = "weather_forecast.db"
//
//        fun getInstance(): AppDataBase {
//            INSTANCE?.let {
//                return it
//            }
//            synchronized(LOCK) {
//                INSTANCE?.let {
//                    return it
//                }
//                val db = Room.databaseBuilder(
//                    WeatherApp.get(),
//                    AppDataBase::class.java,
//                    DB_NAME
//                )
//                    .allowMainThreadQueries()
//                    .fallbackToDestructiveMigration()
//                    .build()
//                INSTANCE = db
//                return db
//            }
//        }
//    }
}