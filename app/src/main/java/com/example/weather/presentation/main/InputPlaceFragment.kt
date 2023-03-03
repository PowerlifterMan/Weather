package com.example.weather.presentation.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.weather.R
import com.example.weather.databinding.FragmentInputPlaceBinding
import com.example.weather.domain.CurrentCity
import com.example.weather.retrofit.daData.CityRvAdapter

class InputPlaceFragment : DialogFragment() {
    //    private val dataModel: InputPlaceViewModel
    lateinit var binding: FragmentInputPlaceBinding

    //    var binding:View?=null
    companion object {
        fun newInstance() = InputPlaceFragment()
    }

    private lateinit var viewModel: InputPlaceViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentInputPlaceBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(InputPlaceViewModel::class.java)
        val listOfCity = viewModel.getListForRv()
        val btnGoRequest = binding.btnSearch
        val stringForSearh: EditText = binding.placeTextInput
        val recyclerView = binding.rvSityName
        recyclerView.layoutManager = LinearLayoutManager(requireActivity(),RecyclerView.VERTICAL,false)
        val cityAdapter = CityRvAdapter()
        recyclerView.adapter = cityAdapter
        cityAdapter.onItemClickListener = object : CityRvAdapter.OnItemClickListener {
            override fun itemClick(item: CurrentCity) {
                val args = Bundle().apply {
                    putParcelable("CITY",item)
                }
                Toast.makeText(requireActivity(), "PRESSED", Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.action_inputPlaceFragment_to_mainFragment,args)
            }
        }
        listOfCity.observe(viewLifecycleOwner) {
                    cityAdapter.submitList(it)
        }
        btnGoRequest.setOnClickListener {
            viewModel.textForSearch.value = stringForSearh.text.toString()
            viewModel.requestCity()

        }
    }
}