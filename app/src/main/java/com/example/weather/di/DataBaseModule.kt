package com.example.weather.di

import android.content.Context
import androidx.room.Room
import com.example.weather.data.room.AppDataBase
import dagger.Module
import dagger.Provides
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
interface DataBaseModule {


    companion object {
        @Singleton
        @Provides
        fun provideDataBase(context: Context): AppDataBase {
            val builder = Room.databaseBuilder(
                context.applicationContext,
                AppDataBase::class.java,
                "weather_forecast"
            )
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
            return builder.build()
        }

    }
}

@Qualifier
annotation class ApplicationContext
