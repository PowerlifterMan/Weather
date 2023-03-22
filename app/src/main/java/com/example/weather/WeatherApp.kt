package com.example.weather

import android.app.Application

class WeatherApp:Application() {
    companion object{
        var INSTANCE: WeatherApp? = null
        fun get(): WeatherApp = INSTANCE!!
    }

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
    }
}