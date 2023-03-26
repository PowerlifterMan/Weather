package com.example.weather.presentation.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weather.data.OpenMeteoRepositoryImpl
import com.example.weather.data.dto.Mappers
import com.example.weather.data.OpenWeatheRepositoryImpl
import com.example.weather.domain.RecyclerViewItem
import com.example.weather.domain.TempOnTime
import com.example.weather.domain.WeatherUseCase
import com.example.weather.data.NinjasRepositoryImpl
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import java.text.SimpleDateFormat
import java.util.*

class MainViewModel : ViewModel() {
    private val ninjasRepository = NinjasRepositoryImpl

    //    private val ninjasUseCase = NinjasUseCase(ninjasRepository)
    private val openWeatherRepository = OpenWeatheRepositoryImpl

    //    private val openWeatherUseCase = WeatherUseCase(openWeatherRepository)
    val sdf = SimpleDateFormat("MM/dd-hh")
    private val openMeteoRepository = OpenMeteoRepositoryImpl

    //    private val openMeteoUseCase = OpenMeteoUseCase(openMeteoRepository)
    private val weatherUseCase = WeatherUseCase()
    var dataSourceType = MutableLiveData<String>()
        private set

    //val currentResponce = openWeatherUseCase.getForecastOpenWeather()
    val c: Int = 2

    var myCityName = MutableLiveData<String>()
        private set(value) {
            field = value
        }
    private val myLongitude = MutableLiveData<Float>()
    private val myLatitude = MutableLiveData<Float>()
    private val myCityCurrentWeather = MutableLiveData<TempOnTime>()
    val rvRow = MutableLiveData<List<RecyclerViewItem>>()

    val mapper = Mappers()

    fun getCity(): LiveData<String> {
        return myCityName
    }

    fun setDataSourceType(sourceName: String) {
        dataSourceType.value = sourceName
    }

    fun getDataSourceType(): LiveData<String> {
        return dataSourceType
    }

    fun getCurrentWeather(): LiveData<TempOnTime> {
        return myCityCurrentWeather
    }

    fun getForecast(): LiveData<List<RecyclerViewItem>> {
        return rvRow
    }

    fun setCurrentCity(lat: Float, lon: Float, city: String) {
        myCityName.value = city
        myLatitude.value = lat
        myLongitude.value = lon
    }

    fun getForecastData(sourceName: String) {
        weatherUseCase.getForecast(
            lat = myLatitude.value ?: 0f,
            lon = myLongitude.value ?: 0f,
            sourceName = sourceName,
            city = myCityName.value ?: ""
        ).observeOn(AndroidSchedulers.mainThread())
            .subscribe ({ data ->
                val tempOnTime = TempOnTime(
                    timestamp = data.currentTemp.timeStamp,
                    temp = data.currentTemp.temperatureMax,
                    tempFeelsLike = data.currentTemp.temperatureFeelsLikeMax
                )
                myCityCurrentWeather.value = tempOnTime
                rvRow.value =  data.forecastList.map { item -> RecyclerViewItem(
                    dayNumber = sdf.format(item.timeStamp.toLong() * 1000),
                    temperature = item.temperatureMax.toString(),
                    description = item.temperatureFeelsLikeMax.toString()
                ) }

            },
        { error ->
            error.printStackTrace()
            Log.e("ERROR", error.message.toString())

        })
//        when (sourceName) {
//            SOURCE_OPEN_METEO -> {
//                openMeteoUseCase.getForecastOpenMeteo(
//                    latitude = myLatitude.value ?: 0f,
//                    longitude = myLongitude.value ?: 0f
//                )
////            .observeOn(Schedulers.computation())
////            .map(mapper)
//                    .observeOn(AndroidSchedulers.mainThread())
//                    .subscribe(
//                        { data ->
//                            val tempOnTime = TempOnTime(
//                                timestamp = data.currentWeather.currentTime.toLong(),
//                                temp = data.currentWeather.temperature,
//                                tempFeelsLike = data.currentWeather.temperature
//                            )
//                            myCityCurrentWeather.value = tempOnTime
//                            val temperatureList = data.daily.temperature_2m
//                            val dateList = data.daily.time
//                            val apparent_temperatureList = data.daily.apparent_temperature_2m
//                            val recyclerViewItemList = mutableListOf<RecyclerViewItem>()
//                            temperatureList.forEachIndexed { index, value ->
//                                recyclerViewItemList.add(
//                                    RecyclerViewItem(
//                                        dayNumber = sdf.format(dateList[index].toLong() * 1000),
//                                        temperature = value.toString(),
//                                        description = apparent_temperatureList[index].toString()
//                                    )
//                                )
//
//                            }
//                            rvRow.value = recyclerViewItemList
//
//                        }, ::onError
//                    )
//
//
//            }
//            SOURCE_NINJAS -> {
//                ninjasUseCase.getWeather(
//                    latitude = myLatitude.value ?: 0f,
//                    longitude = myLongitude.value ?: 0f
//                ).observeOn(AndroidSchedulers.mainThread())
//                    .subscribe({ data -> })
//            }
//            SOURCE_OPEN_WEATHER -> {
//                openWeatherUseCase.getWeatherOpenWeather(
//                    lat = myLatitude.value ?: 0f,
//                    lon = myLongitude.value ?: 0f
//                )
//                    .observeOn(Schedulers.computation())
//                    .map(mapper::mapOpenWeatherToCurrentWeather)
//                    .observeOn(AndroidSchedulers.mainThread())
//                    .subscribe(
//                        { data ->
//                            //        myCityName.value = data.city.name ?: ""
//                            myCityCurrentWeather.value = data.forecastList[0]
//                        }, ::onError
//                    )
//
//                openWeatherUseCase.getOpenWeatherFOrecastData(
//                    lat = myLatitude.value ?: 0f,
//                    lon = myLongitude.value ?: 0f
//                )
//                    .observeOn(Schedulers.computation())
//                    .map(mapper::mapOpenForecastToCityForecast)
//                    .observeOn(AndroidSchedulers.mainThread())
//                    .subscribe({ data ->
//                        //          cityRow.value = data.city.name.orEmpty()
//                        rvRow.value = data.forecastList.map { item ->
//                            RecyclerViewItem(
//                                dayNumber = sdf.format(item.timestamp * 1000),
//                                temperature = (Math.round(item.temp * 10.0) / 10.0).toString(),
//                                description = (Math.round(item.tempFeelsLike * 10) / 10.0).toString()
//
//                            )
//                        }
//                    }, {
//                        it.printStackTrace()
//                    })
//
//
//            }
//        }
//

    }

    private fun onError(error: Throwable) {
        Log.e("MyApp", error.message.toString())
    }

    private fun getDateTime(s: Long): String? {
        try {
            val sdf = SimpleDateFormat("yyyy/MM/dd")
            val netDate = Date(s * 1000)
            return sdf.format(netDate)
        } catch (e: Exception) {
            return e.toString()
        }
    }

}