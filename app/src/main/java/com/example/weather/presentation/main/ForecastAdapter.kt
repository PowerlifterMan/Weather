package com.example.weather.presentation.main

import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.weather.R
import com.example.weather.domain.DayData


class ForecastAdapter :
    ListAdapter<DayData, ForecastAdapter.ForecastViewHolder>(ForecastDiffCallback()) {

    class ForecastViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val tvTemp = view.findViewById<TextureView>(R.id.tvTemp)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForecastViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.forecast_item, parent, false)
        return ForecastViewHolder(view)
    }

    override fun onBindViewHolder(holder: ForecastViewHolder, position: Int) {
        val item = getItem(position)

    }
}