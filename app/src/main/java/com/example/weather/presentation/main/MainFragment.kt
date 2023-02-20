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
import java.text.SimpleDateFormat
import java.util.*


class MainFragment : Fragment() {
    val rvList = mutableListOf<RecyclerViewItem>()
    private lateinit var viewModel: MainViewModel
    private var _binding: FragmentMainBinding? = null
    private val binding: FragmentMainBinding
        get() = _binding ?: throw RuntimeException("FragmentMainBinding? == null")
    val adapter = ForecastAdapter()
    var myCity = CurrentCity()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val list = viewModel.rvRow
        val city = viewModel.cityRow
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
        list.observe(viewLifecycleOwner){
            adapter.submitList(it)
        }
        city.observe(viewLifecycleOwner){
            binding.tvCurrentLocation.text = it

        }


//        viewModel.currentResponce.observe(requireActivity()) { responceBody ->
//            val list = responceBody.list
//            list.forEach {
//                val date = getDateTime(it.dateOfForecast)
//                rvList.add(
//                    RecyclerViewItem(
//                        dayNumber = date.toString(),
//                        temperature = it.mainForecastData.temp.toString(),
//                        description = it.mainForecastData.tempFeels.toString()
//                    )
//                )
//            }
//            adapter.submitList(rvList)
//            binding.tvCurrentLocation.text = responceBody.city.cityName
//        }

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
