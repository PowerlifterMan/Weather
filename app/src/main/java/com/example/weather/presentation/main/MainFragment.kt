package com.example.weather.presentation.main

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
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
import java.lang.RuntimeException


class MainFragment : Fragment() {
    private var _binding: FragmentMainBinding? = null
    private val binding: FragmentMainBinding
        get() = _binding ?: throw RuntimeException("FragmentMainBinding? == null")

    private val adapter = ForecastAdapter()

    private var list = mutableListOf<RecyclerViewItem>()

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        repeat(6) {
            list.add(RecyclerViewItem(dayNumber = it.toString()))
        }
        binding.cardView.setBackgroundResource(R.drawable.nature)
        adapter.onItemClickListener = object : ForecastAdapter.OnItemClickListener {
            override fun itemClick(item: RecyclerViewItem) {
                changeCurrentDayInfo(item)
            }
        }
        adapter.submitList(list)
        binding.forecastRV.layoutManager =
            LinearLayoutManager(requireActivity(), RecyclerView.HORIZONTAL, false)
        binding.forecastRV.adapter = adapter

    }

    private fun changeCurrentDayInfo(item: RecyclerViewItem) {
        Toast.makeText(requireActivity(), "PRESSED", Toast.LENGTH_SHORT).show()
        binding.tvCurrentLocation.text = "CLICKED"
    }

// fun fillList(): List<RecyclerViewItem> {
//
//    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

}