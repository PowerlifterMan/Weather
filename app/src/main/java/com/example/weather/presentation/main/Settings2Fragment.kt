package com.example.weather.presentation.main

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.weather.R
import com.example.weather.databinding.FragmentSettings2Binding
import com.example.weather.databinding.FragmentSettingsBinding
import dagger.android.support.AndroidSupportInjection
import java.lang.RuntimeException
import javax.inject.Inject

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Settings2Fragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class Settings2Fragment @Inject constructor() : Fragment() {
    // TODO: Rename and change types of parameters

    private var param1: String? = null
    private var param2: String? = null
    private var source1checked = false
    private var source2checked = false
    private var source3checked = false
    private lateinit var viewModel: Settings2ViewModel

    @Inject
    lateinit var viemodelFactory: ViewModelFactory

    private var _binding: FragmentSettings2Binding? = null
    private val binding: FragmentSettings2Binding
        get() = _binding ?: throw RuntimeException("FragmentSettings2Binding? == null")


    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidSupportInjection.inject(this)
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this,viemodelFactory).get(Settings2ViewModel::class.java)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSettings2Binding.inflate(inflater, container, false)
        with(binding) {
            setting2BtnOK.setOnClickListener {
                val result = bundleOf(
                    "sourceChecked1" to source1checked,
                    "sourceChecked2" to source2checked,
                    "sourceChecked3" to source3checked  ,
                )
                setFragmentResult(requestKey = "settingsFragment2Checked", result = result)
                findNavController().popBackStack()
            }
            checkBoxOption1.setOnCheckedChangeListener { buttonView, isChecked ->
                source1checked = isChecked
                Log.e("SETTING", "SOURCE 1 IS CHECKED = $isChecked")
            }
            checkBoxOption2.setOnCheckedChangeListener { buttonView, isChecked ->
                source2checked = isChecked
                Log.e("SETTING", "SOURCE 2 IS CHECKED = $isChecked")
            }
            checkBoxOption3.setOnCheckedChangeListener { buttonView, isChecked ->
                source3checked = isChecked
                Log.e("SETTING", "SOURCE 3 CHECKED = $isChecked")
            }

        }

        return binding.root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Settings2Fragment.
         */
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