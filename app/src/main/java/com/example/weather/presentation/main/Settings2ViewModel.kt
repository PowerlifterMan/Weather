package com.example.weather.presentation.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weather.presentation.main.Settings2Fragment.Companion.CHECK_BOX_SOURCE_NINJAS
import com.example.weather.presentation.main.Settings2Fragment.Companion.CHECK_BOX_SOURCE_OPEN_WEATHER
import com.example.weather.presentation.main.Settings2Fragment.Companion.CHECK_BOX_SOURCE_OPEN_METEO
import javax.inject.Inject


class Settings2ViewModel @Inject constructor() : ViewModel() {
    private val _isErrorState = MutableLiveData<Boolean>()
    private val useSource1 = MutableLiveData<Boolean>()
    private val useSource2 = MutableLiveData<Boolean>()
    private val useSource3 = MutableLiveData<Boolean>()

    init {
        useSource1.value = false
        useSource2.value = false
        useSource3.value = false
    }

    val isErrorState: LiveData<Boolean>
        get() = _isErrorState

    fun onCheckBoxChanged(fieldName: String, state: Boolean) {

        when (fieldName) {
            CHECK_BOX_SOURCE_NINJAS -> {
                useSource1.value = state
            }

            CHECK_BOX_SOURCE_OPEN_WEATHER -> {
                useSource2.value = state
            }

            CHECK_BOX_SOURCE_OPEN_METEO -> {
                useSource3.value = state
            }
        }
    }

    fun checkErrorState(): Boolean {
        return useSource1.value == true || useSource2.value == true || useSource3.value == true
    }
}