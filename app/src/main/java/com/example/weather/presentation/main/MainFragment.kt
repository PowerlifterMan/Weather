package com.example.weather.presentation.main

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.weather.R
import com.example.weather.databinding.FragmentMainBinding
import com.example.weather.domain.CurrentCity
import com.example.weather.domain.RecyclerViewItem
import java.lang.RuntimeException
import java.text.SimpleDateFormat
import java.util.*


class MainFragment : Fragment() {
    private lateinit var viewModel: MainViewModel
    private var _binding: FragmentMainBinding? = null
    private val binding: FragmentMainBinding
        get() = _binding ?: throw RuntimeException("FragmentMainBinding? == null")
    val adapter = ForecastAdapter()
    var myCity = CurrentCity()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        viewModel.setCurrentCity(lat = "55.75", lon = "37.61", city = "Москва")

        setFragmentResultListener("requestCity") { requestKey, bundle ->
            val latitude = bundle.getString("lat")
            val longitude = bundle.getString("lon")
            val cityName = bundle.getString("cityName")
            viewModel.setCurrentCity(
                lat = latitude ?: "",
                lon = longitude ?: "",
                city = cityName ?: ""
            )
            viewModel.getForecastData()
        }
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val forecastList = viewModel.getForecast()
        val city = viewModel.myCityName
        val currentWeather = viewModel.getCurrentWeather()
        viewModel.getForecastData()

        with(binding) {
            cardView.setBackgroundResource(R.drawable.low_cloud_cover)
            btnSetCity.setOnClickListener {
                myCity = getNewCity()

            }
            forecastRV.layoutManager =
                LinearLayoutManager(requireActivity(), RecyclerView.VERTICAL, false)
            forecastRV.adapter = adapter
        }
        adapter.onItemClickListener = object : ForecastAdapter.OnItemClickListener {
            override fun itemClick(item: RecyclerViewItem) {
                changeCurrentDayInfo(item)
            }
        }

        forecastList.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }

        city.observe(viewLifecycleOwner) {
            binding.tvCurrentLocation.text = it

        }
        currentWeather.observe(viewLifecycleOwner) {
            binding.tvCurrentTemp.text = "${Math.round(it.temp).toString()} °C"
            binding.tvCaption.text = "ощущается как ${Math.round(it.tempFeelsLike).toString()} °С"
        }
    }

    private fun getNewCity(): CurrentCity {
        findNavController().navigate(R.id.action_mainFragment_to_inputPlaceFragment)
        return myCity
    }

    private fun changeCurrentDayInfo(item: RecyclerViewItem) {
        Toast.makeText(requireActivity(), "PRESSED", Toast.LENGTH_SHORT).show()
        binding.tvCurrentLocation.text = "CLICKED"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }


    private fun getDateTime(s: Long): String? {
        try {
            val sdf = SimpleDateFormat("yyyy/MM/dd")
            val netDate = Date(s * 1000)
            return sdf.format(netDate)
        } catch (e: Exception) {
            return e.toString()
        }
    }

    companion object {
        fun newInstance() = MainFragment()
        const val OPEN_WEATHER_API_KEY = "5d1db59ebde74404687cd4a218b50859"
    }
}
