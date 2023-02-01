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
import com.example.weather.domain.RecyclerViewItem
import com.example.weather.retrofit.GisMeteoCommon
import com.example.weather.retrofit.Suggestions
import com.google.gson.JsonObject
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Response
import java.lang.RuntimeException
import javax.security.auth.callback.Callback


class MainFragment : Fragment() {
    private var _binding: FragmentMainBinding? = null
    private val binding: FragmentMainBinding
        get() = _binding ?: throw RuntimeException("FragmentMainBinding? == null")

    val myService = GisMeteoCommon.retrofitService

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        repeat(6) {
//            list.add(RecyclerViewItem(dayNumber = it.toString()))
//        }
        binding.cardView.setBackgroundResource(R.drawable.low_cloud_cover)
//        adapter.onItemClickListener = object : ForecastAdapter.OnItemClickListener {
//            override fun itemClick(item: RecyclerViewItem) {
//                changeCurrentDayInfo(item)
//            }
//        }
//        adapter.submitList(list)
        binding.forecastRV.layoutManager =
            LinearLayoutManager(requireActivity(), RecyclerView.HORIZONTAL, false)
//        binding.forecastRV.adapter = adapter
        getForecast()
    }

    private fun changeCurrentDayInfo(item: RecyclerViewItem) {
        Toast.makeText(requireActivity(), "PRESSED", Toast.LENGTH_SHORT).show()
        binding.tvCurrentLocation.text = "CLICKED"
    }

    // fun fillList(): List<RecyclerViewItem> {
//
//    }
    fun getForecast(location: String = "Moscow", dayz: Int = 1) {
        val jsonObject = JsonObject()
        jsonObject.addProperty("query", "current/4368/?lang=en")

        val bodyOfQuery = jsonObject.toString()
            .toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())

        val currentQuery =
            myService.getForecasrList(
                token = "56b30cb255.3443075",
                encoding = "deflate",
                query = bodyOfQuery
            ).enqueue(object : retrofit2.Callback<Suggestions> {
                override fun onFailure(call: Call<Suggestions>, throwable: Throwable) {
                    Log.d("AAAA", "ОШИБКА!!!")

                }

                override fun onResponse(
                    call: Call<Suggestions>,
                    response: Response<Suggestions>
                ) {
                    val suggestions = response.body()?.suggestions
                    Log.d("AAAA", suggestions.toString())
//                spisok = mappingToAddresList(suggestions)?.toMutableList() ?: mutableListOf()
//                adapter.setItems(spisok)
//                //               adapter.notifyDataSetChanged()
                }
            })

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    companion object {
        fun newInstance() = MainFragment()
    }
}
