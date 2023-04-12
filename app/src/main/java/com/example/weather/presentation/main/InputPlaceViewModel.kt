package com.example.weather.presentation.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weather.data.GeocodingDTO
import com.example.weather.data.dto.Mappers
import com.example.weather.domain.CurrentCity
import com.example.weather.domain.WeatherUseCase
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import javax.inject.Inject

class InputPlaceViewModel @Inject constructor(
    private val useCase: WeatherUseCase,
    private val mapper: Mappers
) : ViewModel() {
//    private val useCase = WeatherUseCase()
//    val mapper = Mappers()

    val cityList = MutableLiveData<List<GeocodingDTO>>()
    val message: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    private var listForRv = MutableLiveData<List<CurrentCity>>()
        private set(value) {
            field = value
        }
    val textForSearch = MutableLiveData<String>()

    fun getListForRv(): LiveData<List<CurrentCity>> {
        return listForRv
    }

    fun requestCity() {
        val cityNameForSearch =
            if (textForSearch.value != null) textForSearch.value.toString() else "Москва"
        useCase.getCityDto(cityNameForSearch)
//            .observeOn(Schedulers.computation())
//            .map (mapper::mapSuggestionsToCityListItem)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ data ->
                listForRv.value = data.map { item ->
                    CurrentCity(
                        name = item.local_names.language_ru,
                        longitude = item.lon,
                        latitude = item.lat,
                        country = item.country
                    )
                }
                Log.d(" ERROR1", listForRv.value.toString())

            }, {
                it.printStackTrace()
                Log.d(" ERROR1", it.message.toString())
            })
    }

    override fun onCleared() {
        super.onCleared()
    }
}


