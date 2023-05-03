package com.example.weather.presentation.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.weather.databinding.FragmentInputPlaceBinding
import com.example.weather.domain.CurrentCity
import com.example.weather.presentation.main.MainFragment.Companion.CITY_NAME_KEY
import com.example.weather.presentation.main.MainFragment.Companion.INPUT_PLACE_FRAGMENT_DATA
import com.example.weather.presentation.main.MainFragment.Companion.LATITUDE_KEY
import com.example.weather.presentation.main.MainFragment.Companion.LONGITUDE_KEY
import com.example.weather.retrofit.daData.CityRvAdapter
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

class InputPlaceFragment : Fragment()  {
//    val d = DialogFragment()
    private lateinit var viewModel: InputPlaceViewModel
    lateinit var binding: FragmentInputPlaceBinding

    //    var binding:View?=null
    companion object {
        fun newInstance() = InputPlaceFragment()
    }

    @Inject
    lateinit var viemodelFactory: ViewModelFactory
//    private val component = WeatherComponent.

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidSupportInjection.inject(this)
//        val component =
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentInputPlaceBinding.inflate(inflater)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this,viemodelFactory).get(InputPlaceViewModel::class.java)
        val listOfCity = viewModel.getListForRv()
        val btnGoRequest = binding.btnSearch
        val stringForSearh: EditText = binding.placeTextInput
        val recyclerView = binding.rvSityName
        recyclerView.layoutManager =
            LinearLayoutManager(requireActivity(), RecyclerView.VERTICAL, false)
        val cityAdapter = CityRvAdapter()
        recyclerView.adapter = cityAdapter
        cityAdapter.onItemClickListener = object : CityRvAdapter.OnItemClickListener {
            override fun itemClick(item: CurrentCity) {
                val result = bundleOf(
                    LATITUDE_KEY to item.latitude,
                    LONGITUDE_KEY to item.longitude,
                    CITY_NAME_KEY to item.name
                )
                setFragmentResult(INPUT_PLACE_FRAGMENT_DATA, result)
                findNavController().popBackStack()
//                Toast.makeText(requireActivity(), "PRESSED", Toast.LENGTH_SHORT).show()
            }
        }
        listOfCity.observe(viewLifecycleOwner) {
            cityAdapter.submitList(it)
        }
        btnGoRequest.setOnClickListener {
            viewModel.textForSearch.value = stringForSearh.text.toString().trim()
            viewModel.onGoButtonClicked()

        }
    }
}