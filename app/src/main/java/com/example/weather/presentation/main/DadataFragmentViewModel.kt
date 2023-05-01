package com.example.weather.presentation.main

import androidx.lifecycle.ViewModel
import com.example.weather.data.dto.Mappers
import com.example.weather.domain.CurrentCity
import com.example.weather.retrofit.daData.DaDataRepositoryImpl
import com.example.weather.retrofit.daData.DaDataUseCase
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class DadataFragmentViewModel @Inject constructor(): ViewModel() {

    private val repository = DaDataRepositoryImpl
    private val useCase = DaDataUseCase(repository)
    private val mapper = Mappers()
    fun getCity(query: String):Single<List<CurrentCity>> {
        return useCase.getCityDto(query)
            .map {
                mapper.mapSuggestionsToCurrentCity(it)
            }

    }

}