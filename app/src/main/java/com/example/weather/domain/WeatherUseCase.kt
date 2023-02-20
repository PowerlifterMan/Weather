package com.example.weather.domain

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.weather.data.Mappers
import com.example.weather.retrofit.OpenWeatherDto
import com.example.weather.retrofit.openWeather.City
import com.example.weather.retrofit.openWeather.OpenWeatherForecastDTO
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

class WeatherUseCase(private val weatherRepository: OpenWeatherRepository) {
    private val mapper = Mappers()
    private val cityForecastData = MutableLiveData<CityForecastData>()
    private val cityForecast = MutableLiveData<CurrentCity>()
    private val rowForecast = MutableLiveData<List<RecyclerViewItem>>()

    fun getForecastOpenWeather(
        lat: String = DEFAULT_LATITUDE,
        lon: String = DEFAULT_LONGITUDE
    ): Single<OpenWeatherForecastDTO> {
        return weatherRepository.getForecastOpenWeather(lat = lat, lon = lon)
    }

    fun getOpenWeatherFOrecastData(
        lat: String = DEFAULT_LATITUDE,
        lon: String = DEFAULT_LONGITUDE
    ): Single<OpenWeatherForecastDTO> {
//        val disposable =
        return weatherRepository.getForecastOpenWeather(lat = lat, lon = lon)
    }

//    fun getOpenWeatherCity(city)

    fun getWeatherOpenWeather(
        lat: String = DEFAULT_LATITUDE,
        lon: String = DEFAULT_LONGITUDE
    ): Single<OpenWeatherDto> {
        return weatherRepository.getWeatherOpenWeather(lat = lat, lon = lon)
    }

    companion object {
        const val DEFAULT_LATITUDE = "44.044"
        const val DEFAULT_LONGITUDE = "42.86"
    }
}