package com.example.weather.presentation.main

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.weather.R
import com.example.weather.domain.RecyclerViewItem


class ForecastAdapter :
    ListAdapter<RecyclerViewItem, ForecastAdapter.ForecastViewHolder>(ForecastDiffCallback()) {
    var onItemClickListener: OnItemClickListener? = null

    class ForecastViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val tvTemp = view.findViewById<TextView>(R.id.tvTemp)
        val tvFeels = view.findViewById<TextView>(R.id.tvDescr)
        val tvDate = view.findViewById<TextView>(R.id.tvDate)
        val descrIcon = view.findViewById<ImageView>(R.id.imIcon)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForecastViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.forecast_item, parent, false)
        return ForecastViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ForecastViewHolder, position: Int) {
        val item = getItem(position)
        holder.view.setOnClickListener {
            onItemClickListener?.itemClick(item)
        }
        holder.tvDate.text = item.dayNumber
        holder.tvTemp.text = item.temperature + " °C"
        holder.tvFeels.text = "${item.description}, ощущается как ${item.temperatureFeelsLike} °C"

        if (item.pictureUrl?.isNotEmpty() == true) {
//            val glideUrl = "https://openweathermap.org/img/wn/${item.pictureUrl}@2x.png"
            Glide.with(holder.view.context).load("https://openweathermap.org/img/wn/${item.pictureUrl}@2x.png").into(holder.descrIcon)
//            holder.tvFeels.setBackgroundResource()
        }


    }

    interface OnItemClickListener {
        fun itemClick(item: RecyclerViewItem) {

        }
    }
}