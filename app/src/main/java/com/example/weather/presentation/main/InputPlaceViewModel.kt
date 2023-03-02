package com.example.weather.presentation.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weather.data.Mappers
import com.example.weather.data.OpenWeatheRepositoryImpl
import com.example.weather.domain.WeatherUseCase
import com.example.weather.retrofit.daData.CityListItem
import com.example.weather.retrofit.daData.DaDataRepository
import com.example.weather.retrofit.daData.DaDataRepositoryImpl
import com.example.weather.retrofit.daData.DaDataUseCase
import com.example.weather.retrofit.openWeather.GeocodingDTO
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class InputPlaceViewModel : ViewModel() {
    private val repository = OpenWeatheRepositoryImpl
    private val useCase = WeatherUseCase(repository)
    val mapper = Mappers()
    val cityList = MutableLiveData<List<GeocodingDTO>>()
    val message: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }
    private val listForRv = MutableLiveData<List<CityListItem>>()
    val textForSearch = MutableLiveData<String>()

    fun getListForRv(): LiveData<List<CityListItem>> {
        return listForRv
    }

    fun requestCity() {
        val cityNameForSearch =
            if (textForSearch.value != null) textForSearch.value.toString() else "Москва"
        useCase.getCityDto(cityNameForSearch)
//            .observeOn(Schedulers.computation())
//            .map (mapper::mapSuggestionsToCityListItem)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                cityList.value = it

            }, {
                it.printStackTrace()
                Log.d(" ERROR1", it.message.toString())
            })
    }

    override fun onCleared() {
        super.onCleared()
    }
}


