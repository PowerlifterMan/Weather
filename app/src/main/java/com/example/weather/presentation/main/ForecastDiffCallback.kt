package com.example.weather.presentation.main

import androidx.recyclerview.widget.DiffUtil
import com.example.weather.domain.DayData

class ForecastDiffCallback: DiffUtil.ItemCallback<DayData>() {
    override fun areItemsTheSame(oldItem: DayData, newItem: DayData): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: DayData, newItem: DayData): Boolean {
        return oldItem == newItem
    }
}