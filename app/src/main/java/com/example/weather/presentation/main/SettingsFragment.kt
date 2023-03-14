package com.example.weather.presentation.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.weather.R
import com.example.weather.databinding.FragmentSettingsBinding
import java.lang.RuntimeException

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
const val SOURCE_NINJAS = "ninjas"
const val SOURCE_OPEN_METEO = "open_meteo"
const val SOURCE_OPEN_WEATHER = "open_weather"


/**
 * A simple [Fragment] subclass.
 * Use the [SettingsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SettingsFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var currentSource = SOURCE_OPEN_METEO
    private lateinit var viewModel: SettingsViewModel
    private var _binding: FragmentSettingsBinding? = null
    private val binding: FragmentSettingsBinding
        get() = _binding ?: throw RuntimeException("FragmentSettingsBinding? == null")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
        viewModel = ViewModelProvider(this).get(SettingsViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
//    inflater.inflate(R.layout.fragment_settings, container, false)
        binding.btnSubmit.setOnClickListener {
            val result = bundleOf(
                "source" to currentSource
            )
            setFragmentResult("requestDataSource", result)
            findNavController().popBackStack()

        }
        binding.rgChangeSource.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.rbOpenMeteo -> {
                    Toast.makeText(requireContext(), "OPEN METEO", Toast.LENGTH_SHORT)
                        .show()
                    currentSource = SOURCE_OPEN_METEO
                }

                R.id.rbOpenWeather -> {
                    Toast.makeText(
                        requireContext(),
                        "OPEN WEATHER",
                        Toast.LENGTH_SHORT
                    ).show()
                    currentSource = SOURCE_OPEN_WEATHER
                }

                R.id.rbNinjas -> {
                    Toast.makeText(
                        requireContext(),
                        "OPEN WEATHER",
                        Toast.LENGTH_SHORT
                    ).show()
                    currentSource = SOURCE_NINJAS
                }
            }

        }
        binding.rgChangeSource.setOnClickListener {

            changeSource(it.id)
        }
        return binding.root
    }

    private fun changeSource(id: Int) {
        when (id) {
            R.id.rbOpenMeteo -> Toast.makeText(requireContext(), "OPEN METEO", Toast.LENGTH_SHORT)
                .show()
            R.id.rbOpenWeather -> Toast.makeText(
                requireContext(),
                "OPEN WEATHER",
                Toast.LENGTH_SHORT
            ).show()
        }

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SettingsFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String = "", param2: String = "") =
            SettingsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}