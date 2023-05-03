package com.example.weather.presentation.main

import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.material.internal.ContextUtils.getActivity
import javax.inject.Inject


class Settings2ViewModel @Inject constructor() : ViewModel() {
    private val source1checked = MutableLiveData<Boolean>()
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

    fun setChoice(fieldName: String, state: Boolean) {
        when (fieldName) {
            "option1" -> {
                useSource1.value = state
            }

            "option2" -> {
                useSource2.value = state
            }

            "option3" -> {
                useSource3.value = state
            }
        }
    }

    fun checkErrorState(): Boolean {
        return useSource1.value == true || useSource2.value == true || useSource3.value == true
    }
}