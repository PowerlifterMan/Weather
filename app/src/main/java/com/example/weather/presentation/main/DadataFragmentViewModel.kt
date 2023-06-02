package com.example.weather.presentation.main

import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weather.data.dto.Mappers
import com.example.weather.domain.CurrentCity
import com.example.weather.retrofit.daData.DaDataRepositoryImpl
import com.example.weather.retrofit.daData.DaDataUseCase
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.ObservableEmitter
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.DisposableContainer
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class DadataFragmentViewModel @Inject constructor(): ViewModel() {

    private val repository = DaDataRepositoryImpl
    private val useCase = DaDataUseCase(repository)
    private val mapper = Mappers()
    private val disposables = CompositeDisposable()
    private lateinit var inpuEmitter: ObservableEmitter<String>
    private val recyclerViewList = MutableLiveData<List<CurrentCity>>()
    init {
//        val disposable = Observable.create { emitter: ObservableEmitter<String> ->
//        inpuEmitter = emitter
//        }
//            .subscribeOn(Schedulers.io())
//            .filter { it.length > 3 }
//            .debounce(1, TimeUnit.SECONDS)
//            .flatMapSingle { queryString ->
//                Log.e("ERROR2", queryString)
//                getCity(queryString)
//            }
//            .observeOn(Schedulers.io())
//            .subscribe({ currentCityList ->
//                recyclerViewList.postValue(currentCityList)
//            }, {
//                it.printStackTrace()
//                Log.e("ERROR2", it.message.toString())
//            })
//        disposables.add(disposable)
    }

    fun getCityList():LiveData<List<CurrentCity>> = recyclerViewList
    fun getCity(query: String):List<CurrentCity> {
        TODO()
//        return useCase.getCityDto(query)
//            .map {
//                mapper.mapSuggestionsToCurrentCity(it)
//            }
//
    }

    fun onInputTextChanged(newText: String) {
        inpuEmitter.onNext(newText)
    }

    override fun onCleared() {
        super.onCleared()
        disposables.dispose()

    }
}