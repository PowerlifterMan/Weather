package com.example.weather

import android.app.Activity
import android.app.Application
import com.example.weather.di.DaggerWeatherComponent
import com.example.weather.di.WeatherComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

class WeatherApp : Application(), HasAndroidInjector  {
    lateinit var daggerAppComponent: WeatherComponent
    @Inject
    lateinit var activityInjector: DispatchingAndroidInjector<Activity>
    override fun onCreate() {

        daggerAppComponent = DaggerWeatherComponent
            .builder()
            .application(this)
            .withContext(this)
            .build()

        daggerAppComponent.inject(this)
        super.onCreate()
    }
    override fun androidInjector(): AndroidInjector<Any> = activityInjector as AndroidInjector<Any>
}