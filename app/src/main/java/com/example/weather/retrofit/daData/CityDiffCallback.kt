package com.example.weather.retrofit.daData

import androidx.recyclerview.widget.DiffUtil

class CityDiffCallback: DiffUtil.ItemCallback<CityListItem>() {
    override fun areItemsTheSame(oldItem: CityListItem, newItem: CityListItem): Boolean {
            return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: CityListItem, newItem: CityListItem): Boolean {
        return oldItem.unrestrictedAddres == newItem.unrestrictedAddres
    }
}