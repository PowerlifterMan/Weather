package com.example.weather.presentation.main.recyclerViews

import androidx.recyclerview.widget.DiffUtil

class ForecastDiffCallback: DiffUtil.ItemCallback<RecyclerViewRow>() {
    override fun areItemsTheSame(oldItem: RecyclerViewRow, newItem: RecyclerViewRow): Boolean {
        return oldItem.hashCode() == newItem.hashCode()
    }

    override fun areContentsTheSame(oldItem: RecyclerViewRow, newItem: RecyclerViewRow): Boolean {
        return oldItem.equals(newItem)
    }
}