package com.example.weather.presentation.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weather.data.GeocodingDTO
import com.example.weather.data.dto.Mappers
import com.example.weather.domain.CurrentCity
import com.example.weather.domain.WeatherUseCase
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class InputPlaceViewModel @Inject constructor(
    private val useCase: WeatherUseCase,
) : ViewModel() {

    private var listForRv = MutableLiveData<List<CurrentCity>>()
        private set(value) {
            field = value
        }
    val textForSearch = MutableLiveData<String>()
    private val coroutineScopeMain = CoroutineScope(Dispatchers.Main)
    fun getListForRv(): LiveData<List<CurrentCity>> {
        return listForRv
    }

    fun onGoButtonClicked() {
        val cityNameForSearch =
            if (textForSearch.value != null && textForSearch.value.toString()
                    .isNotEmpty()
            ) textForSearch.value.toString() else "Москва"
        coroutineScopeMain.launch {
            val geoCodingDtoList = useCase.getCityDto(cityNameForSearch)
            listForRv.value = geoCodingDtoList.map { item ->
                CurrentCity(
                    name = item.local_names.language_ru,
                    longitude = item.lon,
                    latitude = item.lat,
                    country = item.country
                )
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
    }
}