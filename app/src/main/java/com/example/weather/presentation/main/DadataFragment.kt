package com.example.weather.presentation.main

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
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
import com.example.weather.databinding.FragmentDadataBinding
import com.example.weather.domain.CurrentCity
import com.example.weather.presentation.main.MainFragment.Companion.CITY_KLADR_ID_KEY
import com.example.weather.presentation.main.MainFragment.Companion.CITY_NAME_KEY
import com.example.weather.presentation.main.MainFragment.Companion.DADATA_FRAGMENT_DATA
import com.example.weather.presentation.main.MainFragment.Companion.LATITUDE_KEY
import com.example.weather.presentation.main.MainFragment.Companion.LONGITUDE_KEY
import com.example.weather.retrofit.daData.CityRvAdapter
import dagger.android.support.AndroidSupportInjection
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.ObservableEmitter
import io.reactivex.rxjava3.schedulers.Schedulers
import java.io.FileInputStream
import java.util.concurrent.TimeUnit
import javax.inject.Inject


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [DadataFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DadataFragment @Inject constructor() : Fragment() {
//    private var outTextView: TextView? = null
//    private var editText: EditText? = null

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var _binding: FragmentDadataBinding? = null
    lateinit var viewModel : DadataFragmentViewModel
    private val adapter by lazy { CityRvAdapter() }
    private val binding: FragmentDadataBinding
        get() = _binding ?: throw RuntimeException("FragmentDadataBinding is null")

    @Inject
    lateinit var viemodelFactory: ViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidSupportInjection.inject(this)
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(DadataFragmentViewModel::class.java)

//        viewModel = ViewModelProvider(this,viemodelFactory).get(DadataFragmentViewModel::class.java)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    @SuppressLint("CheckResult")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        val adapter = CityRvAdapter()
        val recyclerView = binding.dadataFragmentRv
        recyclerView.layoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        recyclerView.adapter = adapter
        val editText = binding.DadataEditText
        val listLD = viewModel.getCityList()
        setupFields(editText)
        listLD.observe(viewLifecycleOwner){
            adapter.submitList(it)

        }
        adapter.onItemClickListener = object : CityRvAdapter.OnItemClickListener {
            override fun itemClick(item: CurrentCity) {
                val result = bundleOf(
                    LATITUDE_KEY to item.latitude,
                    LONGITUDE_KEY to item.longitude,
                    CITY_NAME_KEY to item.name,
                    CITY_KLADR_ID_KEY to item.cityKladrId
                )
                setFragmentResult(DADATA_FRAGMENT_DATA, result)
                findNavController().popBackStack()
            }
        }
        binding.fragmentDadataBtnSubmit.setOnClickListener {
        }
    }

    @SuppressLint("CheckResult")
    private fun setupFields(editText: EditText) {
          val watcher: TextWatcher = object : TextWatcher {
                override fun beforeTextChanged(
                    charSequence: CharSequence,
                    start: Int,
                    count: Int,
                    after: Int
                ) = Unit

                override fun onTextChanged(
                    charSequence: CharSequence,
                    start: Int,
                    before: Int,
                    count: Int
                ) = Unit
//                {
//                    viewModel.onInputTextChanged(charSequence.toString())
//                }
                override fun afterTextChanged(editable: Editable) { viewModel.onInputTextChanged(editable.toString())
                }
            }
            editText.addTextChangedListener(watcher)
    }

    @SuppressLint("CheckResult")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDadataBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return binding.root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment DadataFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            DadataFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}