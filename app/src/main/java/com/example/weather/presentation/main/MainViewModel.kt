package com.example.weather.presentation.main

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weather.data.dto.Mappers
import com.example.weather.domain.CurrentCity
import com.example.weather.domain.TempOnTime
import com.example.weather.domain.WeatherUseCase
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
import io.reactivex.rxjava3.core.ObservableEmitter
import io.reactivex.rxjava3.disposables.CompositeDisposable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject


class MainViewModel @Inject constructor(
    private val weatherUseCase: WeatherUseCase
) : ViewModel() {
    private val scope = CoroutineScope(Dispatchers.IO)
    private val disposableBag = CompositeDisposable()
    private val cityRepo = DaDataRepositoryImpl
    private val getCityUseCase = DaDataUseCase(cityRepo)

    //    private var dadataJob = Job()
    private var listOfDataSource = MutableLiveData<List<String>>()
        private set
    var cityListLD = MutableLiveData<List<CurrentCity>>()
        private set
    var myCityName = MutableLiveData<String>()
        private set
    var myCityKladr = MutableLiveData<String>()
        private set
    var currentCityLD = MutableLiveData<CurrentCity>()
        private set

    val chartLineData = MutableLiveData<LineData>()
    val chartBarData = MutableLiveData<BarData>()

    private val myLongitude = MutableLiveData<Float>()

    private val myLatitude = MutableLiveData<Float>()
    private lateinit var inputEmitter: ObservableEmitter<String>
    private val myCityCurrentWeather = MutableLiveData<TempOnTime>()
    val rvRow = MutableLiveData<List<RecyclerViewRow>>()

    val mapper = Mappers()

    init {
//        val disposable = Observable.create { emitter: ObservableEmitter<String> ->
//            inputEmitter = emitter
//        }
//            .subscribeOn(Schedulers.io())
//            .filter { it.length > 3 }
//            .debounce(1, TimeUnit.SECONDS)
//            .flatMapSingle { queryString ->
//                Log.e("ERROR2", queryString)
//                getCityFromDadata(queryString)
//            }
//            .observeOn(Schedulers.io())
//            .subscribe({ currentCityList ->
//                cityListLD.postValue(currentCityList)
//                Log.e("MENU", currentCityList.toString())
//            }, {
//                it.printStackTrace()
//                Log.e("ERROR2", it.message.toString())
//            })
//        disposableBag.add(disposable)
////        val job =
    }

    fun onSearchTextChanged(newText: String) {
        val dadataJob = scope.launch {
            val list = getCityFromDadata(newText)
            cityListLD.postValue(list)
        }

    }

    fun dataSourceIsChanged(sourceName: String) {
//        dataSourceType.value = sourceName
        listOfDataSource.value = listOf(sourceName)
        scope.launch {
            getForecastDataCombine()
        }
    }

    fun listDataSourceIsChanged(list: List<String>) {
        listOfDataSource.value = list
//        getForecastDataCombine()
    }

    fun getCurrentWeather(): LiveData<TempOnTime> {
        return myCityCurrentWeather
    }

    fun getForecast(): LiveData<List<RecyclerViewRow>> {
        return rvRow
    }

    suspend fun getCityFromDadata(query: String): List<CurrentCity> {
        val suggestions = getCityUseCase.getCityDto(query)
        return mapper.mapSuggestionsToCurrentCity(suggestions)
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

    fun currentCityIsChanged(lat: Float, lon: Float, city: String, cityKladr: String) {
        myCityName.value = city
        myLatitude.value = lat
        myLongitude.value = lon
        myCityKladr.value = cityKladr
        currentCityLD.value = CurrentCity(
            name = city,
            longitude = lon.toString()
        )
        scope.launch {
            getForecastDataCombine()
        }
    }

    @SuppressLint("CheckResult")
    fun getForecastDataCombine() {
        scope.launch {
            val weatherData = weatherUseCase.getForecast(
                lat = myLatitude.value ?: 0f,
                lon = myLongitude.value ?: 0f,
                sourceNameList = listOfDataSource.value ?: listOf(SOURCE_OPEN_WEATHER),
                city = myCityName.value ?: "",
                cityKladr = myCityKladr.value ?: ""
            )
            val spisok = mapper.mapWeatherDataToRecyclerViewItem(weatherData)
            val tempOnTime = TempOnTime(
                timestamp = weatherData.currentTemp.timeStamp,
                temp = weatherData.currentTemp.temperatureMax,
                tempFeelsLike = weatherData.currentTemp.temperatureFeelsLikeMax
            )
            myCityCurrentWeather.postValue(tempOnTime)
            rvRow.postValue(spisok)
        }
    }

    override fun onCleared() {
        super.onCleared()
        disposableBag.dispose()
        scope.cancel()
    }
}