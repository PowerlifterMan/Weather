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
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
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

    private var listOfDataSource = MutableLiveData<List<String>>()
        private set

    //val currentResponce = openWeatherUseCase.getForecastOpenWeather()
    val c: Int = 2

    var myCityName = MutableLiveData<String>()
        private set(value) {
            field = value
        }
    val chartLineData = MutableLiveData<LineData>()
    val chartBarData = MutableLiveData<BarData>()

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

    fun setListDataSource(list: List<String>) {
        listOfDataSource.value = list
    }

    fun getListDataSource(): LiveData<List<String>> {
        return listOfDataSource
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

    fun setLineChartData() {
        val chartLabels = mutableListOf<String>()
        rvRow.value?.forEach {
            chartLabels.add(it.dayNumber)
        }
        val lineLabels2 = rvRow.value?.map { it ->
            it.dayNumber
        }
        val lineEntryList = rvRow.value?.mapIndexed { index, item ->
            Entry(item.temperature.toFloat(), index)
        }
        val barEntryList = rvRow.value?.mapIndexed { index, item ->
            BarEntry(item.temperature.toFloat(),index)
        }
        val barDataSet = BarDataSet(barEntryList,"WEATHER")
        val lineDataSet = LineDataSet(lineEntryList, "BLUE LINE")

        chartLineData.value = LineData(chartLabels, lineDataSet)
        chartBarData.value = BarData(chartLabels,barDataSet)

    }

    fun getLineChartData(): LineData? {
        return chartLineData.value
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
            .subscribe({ data ->
                val tempOnTime = TempOnTime(
                    timestamp = data.currentTemp.timeStamp,
                    temp = data.currentTemp.temperatureMax,
                    tempFeelsLike = data.currentTemp.temperatureFeelsLikeMax
                )
                myCityCurrentWeather.value = tempOnTime
                rvRow.value = data.forecastList.map { item ->
                    RecyclerViewItem(
                        dayNumber = sdf.format(item.timeStamp.toLong() * 1000),
                        temperature = item.temperatureMax.toString(),
                        description = item.temperatureFeelsLikeMax.toString()
                    )
                }
                setLineChartData()
            },
                { error ->
                    error.printStackTrace()
                    Log.e("ERROR", error.message.toString())

                })

    }

    fun getForecastDataCombine() {
        weatherUseCase.getForecast(
            lat = myLatitude.value ?: 0f,
            lon = myLongitude.value ?: 0f,
            sourceNameList = listOfDataSource.value ?: listOf(SOURCE_OPEN_WEATHER),
            city = myCityName.value ?: ""
        ).observeOn(AndroidSchedulers.mainThread())
            .subscribe({ data ->
                val tempOnTime = TempOnTime(
                    timestamp = data.currentTemp.timeStamp,
                    temp = data.currentTemp.temperatureMax,
                    tempFeelsLike = data.currentTemp.temperatureFeelsLikeMax
                )
                myCityCurrentWeather.value = tempOnTime
                rvRow.value = data.forecastList.map { item ->
                    RecyclerViewItem(
                        dayNumber = sdf.format(item.timeStamp.toLong() * 1000),
                        temperature = item.temperatureMax.toString(),
                        description = item.temperatureFeelsLikeMax.toString()
                    )
                }

            },
                { error ->
                    error.printStackTrace()
                    Log.e("ERROR", error.message.toString())

                })

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