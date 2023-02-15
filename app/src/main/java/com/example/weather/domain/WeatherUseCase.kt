package com.example.weather.domain

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.weather.data.Mappers
import com.example.weather.retrofit.OpenWeatherDto
import com.example.weather.retrofit.openWeather.OpenWeatherForecastDTO

class WeatherUseCase(private val weatherRepository: OpenWeatherRepository) {
    private val mapper = Mappers()
    private val cityForecastData = MutableLiveData<CityForecastData>()
    private val cityForecast = MutableLiveData<String>()
    private val rowForecast = MutableLiveData<List<RecyclerViewItem>>()

    fun getForecastOpenWeather(
        lat: String = DEFAULT_LATITUDE,
        lon: String = DEFAULT_LONGITUDE
    ): LiveData<OpenWeatherForecastDTO> {

        return weatherRepository.getForecastOpenWeather(lat = lat, lon = lon)
    }

    fun getOpenWeatherData(
        lat: String = DEFAULT_LATITUDE,
        lon: String = DEFAULT_LONGITUDE

    ):LiveData<CityForecastData>{
        val data = weatherRepository.getForecastOpenWeather(lat = lat, lon = lon)
        cityForecastData.value = mapper.mapOpenForecastToCityForecast(data.value)
        return cityForecastData
    }

//    fun getOpenWeatherCity(city)

    fun getWeatherOpenWeather(
        lat: String = DEFAULT_LATITUDE,
        lon: String = DEFAULT_LONGITUDE
    ): LiveData<OpenWeatherDto> {
        return weatherRepository.getWeatherOpenWeather(lat = lat, lon = lon)
    }

    companion object {
        const val DEFAULT_LATITUDE = "44.044"
        const val DEFAULT_LONGITUDE = "42.86"
    }
}