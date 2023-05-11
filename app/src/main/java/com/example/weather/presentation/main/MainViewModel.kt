package com.example.weather.presentation.main

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weather.data.OpenMeteoRepositoryImpl
import com.example.weather.data.dto.Mappers
import com.example.weather.data.OpenWeatheRepositoryImpl
import com.example.weather.domain.TempOnTime
import com.example.weather.domain.WeatherUseCase
import com.example.weather.data.NinjasRepositoryImpl
import com.example.weather.domain.CurrentCity
import com.example.weather.presentation.main.recyclerViews.RecyclerViewItem
import com.example.weather.presentation.main.recyclerViews.RecyclerViewRow
import com.example.weather.retrofit.daData.DaDataRepositoryImpl
import com.example.weather.retrofit.daData.DaDataUseCase
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.ObservableEmitter
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject


class MainViewModel @Inject constructor(
    private val weatherUseCase: WeatherUseCase
) : ViewModel() {

    //    private val ninjasUseCase = NinjasUseCase(ninjasRepository)

    //    private val openWeatherUseCase = WeatherUseCase(openWeatherRepository)
    val sdf = SimpleDateFormat("MM/dd-hh")
    var dataSourceType = MutableLiveData<String>()
        private set
    private val disposables = CompositeDisposable()
    private val cityRepo = DaDataRepositoryImpl
    private val getCityUseCase = DaDataUseCase(cityRepo)
    private var listOfDataSource = MutableLiveData<List<String>>()
        private set
    private val cityListLD = MutableLiveData<List<CurrentCity>>()

    //val currentResponce = openWeatherUseCase.getForecastOpenWeather()
    val c: Int = 2

    var myCityName = MutableLiveData<String>()
        private set(value) {
            field = value
        }
    var myCityKladr = MutableLiveData<String>()
        private set
    var currentCityLD = MutableLiveData<CurrentCity>()
        private set

    val chartLineData = MutableLiveData<LineData>()
    val chartBarData = MutableLiveData<BarData>()

    private val myLongitude = MutableLiveData<Float>()

    private val myLatitude = MutableLiveData<Float>()
    private lateinit var inpuEmitter: ObservableEmitter<String>
    private val myCityCurrentWeather = MutableLiveData<TempOnTime>()
    val rvRow = MutableLiveData<List<RecyclerViewRow>>()

    val mapper = Mappers()

    fun getCity(): LiveData<String> {
        return myCityName
    }

    init {
        val disposable = Observable.create { emitter: ObservableEmitter<String> ->
            inpuEmitter = emitter
        }
            .subscribeOn(Schedulers.io())
            .filter { it.length > 3 }
            .debounce(1, TimeUnit.SECONDS)
            .flatMapSingle { queryString ->
                Log.e("ERROR2", queryString)
                getCityFromDadata(queryString)
            }
            .observeOn(Schedulers.io())
            .subscribe({ currentCityList ->
                cityListLD.postValue(currentCityList)
            }, {
                it.printStackTrace()
                Log.e("ERROR2", it.message.toString())
            })
        disposables.add(disposable)

    }

    fun onSearchTextChanged(newText: String) {
        Log.e("TEXTCHANGE", newText)

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

    fun getForecast(): LiveData<List<RecyclerViewRow>> {
        return rvRow
    }

    fun getCityFromDadata(query: String): Single<List<CurrentCity>> {
        return getCityUseCase.getCityDto(query)
            .map {
                mapper.mapSuggestionsToCurrentCity(it)
            }

    }

    fun setLineChartData() {
        val chartLabels = rvRow.value?.filter { it -> it is RecyclerViewItem }?.map { item ->
            (item as RecyclerViewItem).dayNumber
        }
        val lineEntryList =
            rvRow.value?.filter { it -> it is RecyclerViewItem }?.mapIndexed { index, item ->
                Entry((item as RecyclerViewItem).temperature.toFloat(), index)
            }
        val barEntryList =
            rvRow.value?.filter { it -> it is RecyclerViewItem }?.mapIndexed { index, item ->
                BarEntry((item as RecyclerViewItem).temperature.toFloat(), index)
            }
        val barDataSet = BarDataSet(barEntryList, "WEATHER")
        val lineDataSet = LineDataSet(lineEntryList, "BLUE LINE")

        chartLineData.value = LineData(chartLabels, lineDataSet)
        chartBarData.value = BarData(chartLabels, barDataSet)

    }

    fun getChartData(): LineData? {
        return chartLineData.value
    }

    fun setCurrentCity(lat: Float, lon: Float, city: String, cityKladr: String) {
        myCityName.value = city
        myLatitude.value = lat
        myLongitude.value = lon
        myCityKladr.value = cityKladr
        currentCityLD.value = CurrentCity(
            name = city,
            longitude = lon.toString()
        )
    }

    @SuppressLint("CheckResult")
    fun getForecastData(sourceName: String) {
        weatherUseCase.getForecast(
            lat = myLatitude.value ?: 0f,
            lon = myLongitude.value ?: 0f,
            sourceName = sourceName,
            city = myCityName.value ?: "",
            cityKladr = myCityKladr.value ?: ""
        ).observeOn(AndroidSchedulers.mainThread())
            .subscribe({ data ->
                val tempOnTime = TempOnTime(
                    timestamp = data.currentTemp.timeStamp,
                    temp = data.currentTemp.temperatureMax,
                    tempFeelsLike = data.currentTemp.temperatureFeelsLikeMax,
                )
                myCityCurrentWeather.value = tempOnTime
                val spisok = mapper.mapWeatherDataToRecyclerViewItem(data)
                rvRow.value = data.forecastList.map { item ->
                    RecyclerViewItem(
                        dayNumber = sdf.format(item.timeStamp.toLong() * 1000),
                        temperature = item.temperatureMax.toString(),
                        description = item.condition.toString(),
                        pictureUrl = item.conditionIconId
                    )
                }
                setLineChartData()
            },
                { error ->
                    error.printStackTrace()
                    Log.e("ERROR", error.message.toString())

                })

    }

    @SuppressLint("CheckResult")
    fun getForecastDataCombine() {
        weatherUseCase.getForecast(
            lat = myLatitude.value ?: 0f,
            lon = myLongitude.value ?: 0f,
            sourceNameList = listOfDataSource.value ?: listOf(SOURCE_OPEN_WEATHER),
            city = myCityName.value ?: "",
            cityKladr = myCityKladr.value ?: ""
        ).observeOn(AndroidSchedulers.mainThread())
            .subscribe({ data ->
                val spisok = mapper.mapWeatherDataToRecyclerViewItem(data)
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
                        temperatureFeelsLike = item.temperatureFeelsLikeMax.toString(),
                        description = item.condition.toString(),
                        pictureUrl = item.conditionIconId

                    )
                }
                rvRow.value = spisok

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

    override fun onCleared() {
        super.onCleared()
        disposables.dispose()
    }
}