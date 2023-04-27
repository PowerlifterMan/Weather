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
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.room.util.query
import com.example.weather.databinding.FragmentDadataBinding
import com.example.weather.retrofit.daData.DaDataRepository
import com.example.weather.retrofit.daData.DaDataRepositoryImpl
import com.example.weather.retrofit.daData.DaDataUseCase
import com.example.weather.retrofit.daData.DadataCommon
import com.google.gson.JsonObject
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.ObservableEmitter
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.schedulers.Schedulers
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import java.util.concurrent.TimeUnit


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
class DadataFragment : Fragment() {
//    private var outTextView: TextView? = null
//    private var editText: EditText? = null

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private val repository = DaDataRepositoryImpl
    private val useCase =  DaDataUseCase(repository)
    private var _binding: FragmentDadataBinding? = null
    private val binding: FragmentDadataBinding
        get() = _binding ?: throw RuntimeException("FragmentDadataBinding is null")

    override fun onCreate(savedInstanceState: Bundle?) {
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
        val editText = binding.DadataEditText
        val outTextView = binding.dDataTextView
        setupFields(editText, outTextView)
        binding.fragmentDadataBtnSubmit.setOnClickListener {
            val jsonObject = JsonObject()
            jsonObject.addProperty("query", "Ессентуки")
            //            jsonObject.addProperty("query", editText.text.toString())
//            jsonObject.addProperty("from_bound", "city")
//            jsonObject.addProperty("to_bound", "city")
            //    jsonObject.addProperty("query", "Ессентуки")
            val bodyRequest = jsonObject.toString()
                .toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())
            myService.getAddrdessesList(
                contentType = "application/json",
                accept = "application/json",
                token = "9e01e829bc289bb130dbf457fce0d371f44d487f",
                query = bodyRequest
            ).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                        Log.e("ERROR", it.toString())

                    },
                    { error -> error.printStackTrace() }
                )
        }
    }

    @SuppressLint("CheckResult")
    private fun setupFields(editText: EditText, outTextView: TextView) {
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
            .filter { it.length > 3 }
            .debounce(1, TimeUnit.SECONDS)
            .observeOn(Schedulers.io())
            .flatMapSingle { queryString ->
                Log.e("ERROR",queryString)
                useCase.getCityDto(queryString)
            }
            .map { suggestList ->

            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ rvList ->

            },{
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