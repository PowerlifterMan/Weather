package com.example.weather.presentation.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.example.weather.R
import com.example.weather.databinding.FragmentInputPlaceBinding

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
//        return inflater.inflate(R.layout.fragment_input_place, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(InputPlaceViewModel::class.java)
        //   retrofit
//        binding.placeTextInput.addTextChangedListener {
//            dataModel.message.value=it.toString()
//        }
//
        /*      viewModel.message.observe(this,{
            }
            )*/
        // TODO: Use the ViewModel
    }

}