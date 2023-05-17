package com.example.weather.presentation.main

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.weather.databinding.FragmentSettings2Binding
import com.example.weather.presentation.main.MainFragment.Companion.SETTING_FRAGMENT_DATA
import dagger.android.support.AndroidSupportInjection
import java.lang.RuntimeException
import javax.inject.Inject

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

/**
 * A simple [Fragment] subclass.
 * Use the [Settings2Fragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class Settings2Fragment @Inject constructor() : Fragment() {
    // TODO: Rename and change types of parameters

    private var param1: String? = null
    private var param2: String? = null
    private lateinit var viewModel: Settings2ViewModel

    @Inject
    lateinit var viemodelFactory: ViewModelFactory

    private var _binding: FragmentSettings2Binding? = null
    private val binding: FragmentSettings2Binding
        get() = _binding ?: throw RuntimeException("FragmentSettings2Binding? == null")


    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidSupportInjection.inject(this)
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this, viemodelFactory).get(Settings2ViewModel::class.java)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentSettings2Binding.inflate(inflater, container, false)
        with(binding) {
            setting2BtnOK.setOnClickListener {
                if (viewModel.checkErrorState()) {
                    val result = bundleOf(
                        CHECK_BOX_SOURCE_NINJAS to checkBoxOption1.isChecked,
                        CHECK_BOX_SOURCE_OPEN_WEATHER to checkBoxOption2.isChecked,
                        CHECK_BOX_SOURCE_OPEN_METEO to checkBoxOption3.isChecked,
                    )
                    setFragmentResult(requestKey = SETTING_FRAGMENT_DATA, result = result)
                    findNavController().popBackStack()
                }
                else
                {
                    Toast.makeText(requireContext(), "CHEK", Toast.LENGTH_SHORT).show()
                }
            }
            checkBoxOption1.setOnCheckedChangeListener { buttonView, isChecked ->
                viewModel.onCheckBoxChanged(CHECK_BOX_SOURCE_NINJAS, isChecked)
                Log.e("SETTING", "SOURCE 1 IS CHECKED = $isChecked")
            }
            checkBoxOption2.setOnCheckedChangeListener { buttonView, isChecked ->
                viewModel.onCheckBoxChanged(CHECK_BOX_SOURCE_OPEN_WEATHER, isChecked)
                Log.e("SETTING", "SOURCE 2 IS CHECKED = $isChecked")
            }
            checkBoxOption3.setOnCheckedChangeListener { buttonView, isChecked ->
                viewModel.onCheckBoxChanged(CHECK_BOX_SOURCE_OPEN_METEO, isChecked)
                Log.e("SETTING", "SOURCE 3 CHECKED = $isChecked")
            }

        }

        return binding.root
    }

    companion object {
        private const val ARG_PARAM1 = "param1"
        private const val ARG_PARAM2 = "param2"
        const val CHECK_BOX_SOURCE_NINJAS = "NINJAS_SOURCE"
        const val CHECK_BOX_SOURCE_OPEN_WEATHER = "OPEN_WEATHER_SOURCE"
        const val CHECK_BOX_SOURCE_OPEN_METEO = "OPEN_METEO_SOURCE"
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Settings2Fragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
