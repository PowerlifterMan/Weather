package com.example.weather.presentation.main

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
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
    private val sourceList = mutableListOf<String>()
    private var _binding: FragmentMainBinding? = null
    private var currentSourceName: String = SOURCE_OPEN_WEATHER
    private val binding: FragmentMainBinding
        get() = _binding ?: throw RuntimeException("FragmentMainBinding? == null")
    val adapter = ForecastAdapter()
    var myCity = CurrentCity()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        viewModel.setCurrentCity(lat = 55.75f, lon = 37.61f, city = "Москва")
        viewModel.setDataSourceType(currentSourceName)

        setFragmentResultListener("requestDataSource") { requestKey, bundle ->
            currentSourceName = bundle.getString("source") ?: SOURCE_OPEN_WEATHER
            viewModel.setDataSourceType(currentSourceName)
            viewModel.getForecastData(currentSourceName)
        }
        setFragmentResultListener("settingsFragment2Checked") { requestKey, bundle ->
            sourceList.clear()
            if (bundle.getBoolean("sourceChecked1")) sourceList.add(SOURCE_NINJAS)
            if (bundle.getBoolean("sourceChecked2")) sourceList.add(SOURCE_OPEN_WEATHER)
            if (bundle.getBoolean("sourceChecked3")) sourceList.add(SOURCE_OPEN_METEO)
            Log.e("SETTING", sourceList.toString())
            if (sourceList.isNotEmpty()) {
                viewModel.setListDataSource(sourceList)
            }
        }
        setFragmentResultListener("requestCity") { requestKey, bundle ->
            val latitude = bundle.getString("lat")
            val longitude = bundle.getString("lon")
            val cityName = bundle.getString("cityName")
            viewModel.setCurrentCity(
                lat = latitude?.toFloatOrNull() ?: 0f,
                lon = longitude?.toFloatOrNull() ?: 0f,
                city = cityName ?: ""
            )
            viewModel.getForecastData(currentSourceName)
        }
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val forecastList = viewModel.getForecast()
        val city = viewModel.myCityName
        val currentWeather = viewModel.getCurrentWeather()
        val dataSourceTypeLD = viewModel.dataSourceType
        viewModel.getForecastData(currentSourceName)

        with(binding) {
            cardView.setBackgroundResource(R.drawable.low_cloud_cover)
            tvLocation.setOnClickListener {
                myCity = getNewCity()

            }
            binding.btnChangeSettings.setOnClickListener {
                changeSettings()
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
            binding.tvLocation.text = it
        }

//        dataSourceTypeLD.observe(viewLifecycleOwner){
//            viewModel.getForecastData(currentSourceName)
//        }

        currentWeather.observe(viewLifecycleOwner) {
            binding.tvCurrentLocation.text = currentSourceName
            binding.tvCurrentTemp.text = "${Math.round(it.temp).toString()} °C"
            binding.tvCaption.text =
                "ощущается как ${(Math.round(it.tempFeelsLike * 10) / 10).toString()} °С"
        }
    }

    private fun changeSettings() {
        /* старое**
        findNavController().navigate(R.id.action_mainFragment_to_settingsFragment)
        */
        findNavController().navigate(R.id.action_mainFragment_to_settings2Fragment)
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
