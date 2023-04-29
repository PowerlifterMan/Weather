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
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.util.query
import com.example.weather.data.dto.Mappers
import com.example.weather.databinding.FragmentDadataBinding
import com.example.weather.domain.CurrentCity
import com.example.weather.retrofit.daData.CityRvAdapter
import com.example.weather.retrofit.daData.DaDataRepository
import com.example.weather.retrofit.daData.DaDataRepositoryImpl
import com.example.weather.retrofit.daData.DaDataUseCase
import com.example.weather.retrofit.daData.DadataCommon
import com.google.gson.JsonObject
import dagger.android.support.AndroidSupportInjection
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.ObservableEmitter
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.schedulers.Schedulers
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import java.util.concurrent.TimeUnit
import javax.inject.Inject


//import com.jakewharton.rxbinding4.widget.tex

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [DadataFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DadataFragment @Inject constructor(): Fragment() {
//    private var outTextView: TextView? = null
//    private var editText: EditText? = null

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private val repository = DaDataRepositoryImpl
    private val useCase = DaDataUseCase(repository)
    private val mapper = Mappers()
    private var _binding: FragmentDadataBinding? = null
    val adapter by lazy { CityRvAdapter()}
    private val binding: FragmentDadataBinding
        get() = _binding ?: throw RuntimeException("FragmentDadataBinding is null")

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidSupportInjection.inject(this)
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    @SuppressLint("CheckResult")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val myService = DadataCommon.retrofitService
//        val adapter = CityRvAdapter()
        val recyclerView = binding.dadataFragmentRv
        recyclerView.layoutManager = LinearLayoutManager(requireContext(),RecyclerView.VERTICAL,false)
        recyclerView.adapter = adapter
        val editText = binding.DadataEditText
        setupFields(editText)
        adapter.onItemClickListener = object : CityRvAdapter.OnItemClickListener{
            override fun itemClick(item: CurrentCity) {
                val result = bundleOf(
                    "lat" to item.latitude,
                    "lon" to item.longitude,
                    "cityName" to item.name,
                    "cityKladrId" to item.cityKladrId
                )
                setFragmentResult("cityFromDadata",result)
                findNavController().popBackStack()
            }
        }
        binding.fragmentDadataBtnSubmit.setOnClickListener {
        }
    }

    @SuppressLint("CheckResult")
    private fun setupFields(editText: EditText) {
        Observable.create { emitter: ObservableEmitter<String> ->
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

                override fun afterTextChanged(editable: Editable) {
                    if (!emitter.isDisposed) { //если еще не отписались
                        emitter.onNext(editable.toString()) //отправляем текущее состояние
                    }
                }
            }
            emitter.setCancellable {
                editText.removeTextChangedListener(
                    watcher
                )
            } //удаляем листенер при отписке от observable
            editText.addTextChangedListener(watcher)
        }
            .subscribeOn(Schedulers.computation())
            .filter { it.length > 3 }
            .debounce(1, TimeUnit.SECONDS)
            .observeOn(Schedulers.io())
            .flatMapSingle { queryString ->
                Log.e("ERROR", queryString)
                useCase.getCityDto(queryString)
            }
            .map {
                mapper.mapSuggestionsToCurrentCity(it)
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ rvList ->
                    adapter.submitList(rvList)
            }, {
                it.printStackTrace()
            })


    }

    @SuppressLint("CheckResult")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
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