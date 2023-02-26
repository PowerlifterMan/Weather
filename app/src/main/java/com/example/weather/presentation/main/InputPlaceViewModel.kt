package com.example.weather.presentation.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weather.data.Mappers
import com.example.weather.retrofit.daData.CityListItem
import com.example.weather.retrofit.daData.DaDataRepository
import com.example.weather.retrofit.daData.DaDataRepositoryImpl
import com.example.weather.retrofit.daData.DaDataUseCase
import io.reactivex.rxjava3.schedulers.Schedulers

class InputPlaceViewModel : ViewModel() {
    private val repository = DaDataRepositoryImpl
    private val useCase = DaDataUseCase(repository)
    val mapper = Mappers()
    val message: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }
    private val listForRv = MutableLiveData<List<CityListItem>>()
    val textForSearch = MutableLiveData<String>()

    fun getListForRv():LiveData<List<CityListItem>>{
        return listForRv
    }
    fun requestCity() {
        val cityNameForSearch =
            if (textForSearch.value != null) textForSearch.value.toString() else "Москва"
        useCase.getCityDto(cityNameForSearch)
            .observeOn(Schedulers.computation())
            .map (mapper::mapSuggestionsToCityListItem)
            .subscribe({
                listForRv.value = it
            },{
                it.printStackTrace()
            })
        }

    }


