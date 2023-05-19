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
import io.reactivex.rxjava3.disposables.CompositeDisposable
import javax.inject.Inject

class InputPlaceViewModel @Inject constructor(
    private val useCase: WeatherUseCase,
//    private val mapper: Mappers
) : ViewModel() {
//    private val useCase = WeatherUseCase()
    val mapper = Mappers()
    private val disposeBag = CompositeDisposable()
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

    fun onGoButtonClicked() {
        val cityNameForSearch =
            if (textForSearch.value != null) textForSearch.value.toString() else "Москва"
        val disposable = useCase.getCityDto(cityNameForSearch)
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
        disposeBag.add(disposable)
    }

    override fun onCleared() {
        super.onCleared()
        disposeBag.clear()
    }
}


