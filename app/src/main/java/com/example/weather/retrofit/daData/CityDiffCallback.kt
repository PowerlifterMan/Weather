package com.example.weather.retrofit.daData

import androidx.recyclerview.widget.DiffUtil
import com.example.weather.domain.CurrentCity

class CityDiffCallback: DiffUtil.ItemCallback<CurrentCity>() {
    override fun areItemsTheSame(oldItem: CurrentCity, newItem: CurrentCity): Boolean {
            return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: CurrentCity, newItem: CurrentCity): Boolean {
        return oldItem.name == newItem.name
    }
}