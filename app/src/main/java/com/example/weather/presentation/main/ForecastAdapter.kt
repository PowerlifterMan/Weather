package com.example.weather.presentation.main

import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.weather.R
import com.example.weather.domain.DayData
import com.example.weather.domain.RecyclerViewItem


class ForecastAdapter :
    ListAdapter<RecyclerViewItem, ForecastAdapter.ForecastViewHolder>(ForecastDiffCallback()) {
    var onItemClickListener: OnItemClickListener? = null
    class ForecastViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val tvTemp = view.findViewById<TextView>(R.id.tvTemp)
        val tvFeels = view.findViewById<TextView>(R.id.tvDescr)
        val tvDate =  view.findViewById<TextView>(R.id.tvDate)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForecastViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.forecast_item, parent, false)
        return ForecastViewHolder(view)
    }

    override fun onBindViewHolder(holder: ForecastViewHolder, position: Int) {
        val item = getItem(position)
        holder.view.setOnClickListener {
            onItemClickListener?.itemClick(item)
        }
        holder.tvDate.text = item.dayNumber
        holder.tvTemp.text = item.temperature + " °C"
        holder.tvFeels.text = "ощущается как ${item.description} °C"

    }

    interface OnItemClickListener {
        fun itemClick(item: RecyclerViewItem) {

        }
    }
}