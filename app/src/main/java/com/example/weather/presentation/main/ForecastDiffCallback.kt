package com.example.weather.presentation.main

import androidx.recyclerview.widget.DiffUtil
import com.example.weather.domain.RecyclerViewItem

class ForecastDiffCallback: DiffUtil.ItemCallback<RecyclerViewItem>() {
    override fun areItemsTheSame(oldItem: RecyclerViewItem, newItem: RecyclerViewItem): Boolean {
        return oldItem.hashCode() == newItem.hashCode()
    }

    override fun areContentsTheSame(oldItem: RecyclerViewItem, newItem: RecyclerViewItem): Boolean {
        return oldItem == newItem
    }
}