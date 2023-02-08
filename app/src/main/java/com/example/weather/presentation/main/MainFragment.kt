package com.example.weather.presentation.main

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.weather.R
import com.example.weather.databinding.FragmentMainBinding
import com.example.weather.domain.CurrentCity
import com.example.weather.domain.RecyclerViewItem
import com.example.weather.retrofit.openWeather.OpenWeatherCommon
import com.example.weather.retrofit.OpenWeatherDto
import com.example.weather.retrofit.openWeather.OpenWeatherForecastDTO
import retrofit2.Call
import retrofit2.Response
import java.lang.RuntimeException
import java.sql.Timestamp
import java.util.*


class MainFragment : Fragment() {
    val myService = OpenWeatherCommon.retrofitService

    var currentWeather: OpenWeatherDto? = null
    var forecast: OpenWeatherDto? = null
    private var _binding: FragmentMainBinding? = null
    private val binding: FragmentMainBinding
        get() = _binding ?: throw RuntimeException("FragmentMainBinding? == null")

    val adapter = ForecastAdapter()

    //    val myService = OpenWeatherCommon.retrofitService
    var myCity = CurrentCity()
    private lateinit var viewModel: MainViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            cardView.setBackgroundResource(R.drawable.low_cloud_cover)
            btnSetCity.setOnClickListener {
                myCity = getNewCity()

            }
            forecastRV.layoutManager =
                LinearLayoutManager(requireActivity(), RecyclerView.HORIZONTAL, false)
            forecastRV.adapter = adapter
        }
        adapter.onItemClickListener = object : ForecastAdapter.OnItemClickListener {
            override fun itemClick(item: RecyclerViewItem) {
                changeCurrentDayInfo(item)
            }
        }
        adapter.submitList(getForecast())

//        getForecast(lat = "44.045", lon = "42.857")
    }

    private fun getNewCity(): CurrentCity {


        return myCity
    }

    private fun changeCurrentDayInfo(item: RecyclerViewItem) {
        Toast.makeText(requireActivity(), "PRESSED", Toast.LENGTH_SHORT).show()
        binding.tvCurrentLocation.text = "CLICKED"
    }

    // fun fillList(): List<RecyclerViewItem> {
//
//    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    fun getForecast(lat: String = "44.34", lon: String = "10.99", dayz: Int = 3):List<RecyclerViewItem> {
        val rvList  = mutableListOf <RecyclerViewItem>()
        myService.getForecastByCoorddinates(
            latitude = lat,
            longitude = lon,
            appId = MainFragment.OPEN_WEATHER_API_KEY,
            units = "metric",
            lang = "ru",
            nDays = dayz
        ).enqueue(object : retrofit2.Callback<OpenWeatherForecastDTO> {
            override fun onFailure(call: Call<OpenWeatherForecastDTO>, throwable: Throwable) {
                Log.d("AAAA", "ОШИБКА!!!")
            }

            override fun onResponse(
                call: Call<OpenWeatherForecastDTO>,
                response: Response<OpenWeatherForecastDTO>
            ) {
                val myCity = response.body()?.city
                val forecast1 = response.body()?.list
                forecast1?.forEach {
                    val stamp = Timestamp(it.dateOfForecast)
                    rvList.add(RecyclerViewItem(dayNumber = Date(stamp.time).toString(),
                        temperature = it.mainForecastData.temp.toString(),
                        description = it.mainForecastData.tempFeels.toString()))
                }
                val date = response.body()?.list?.get(0)?.dateOfForecast
            }
        })
        return rvList
    }

    companion object {
        fun newInstance() = MainFragment()
        const val OPEN_WEATHER_API_KEY = "5d1db59ebde74404687cd4a218b50859"
    }
}
