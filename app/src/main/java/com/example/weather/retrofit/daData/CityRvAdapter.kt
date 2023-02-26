package com.example.weather.retrofit.daData

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.weather.R
import com.example.weather.domain.RecyclerViewItem

class CityRvAdapter :
    ListAdapter<CityListItem, CityRvAdapter.CityViewHolder>(CityDiffCallback()) {
    var onItemClickListener: OnItemClickListener? = null

    class CityViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val cityRow = view.findViewById<TextView>(android.R.id.text1)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityViewHolder {
        val view =
            LayoutInflater.from(parent.context)
                .inflate(android.R.layout.simple_list_item_1, parent, false)
        return CityViewHolder(view)
    }

    override fun onBindViewHolder(holder: CityViewHolder, position: Int) {
        val item = getItem(position)
        holder.view.setOnClickListener {
            onItemClickListener?.itemClick(item)
        }
        holder.cityRow.text = item.unrestrictedAddres
    }

    interface OnItemClickListener {
        fun itemClick(item: CityListItem) {

        }
    }
}