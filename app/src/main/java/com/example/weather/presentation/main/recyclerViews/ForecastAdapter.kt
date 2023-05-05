package com.example.weather.presentation.main.recyclerViews

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
import java.lang.RuntimeException


class ForecastAdapter :
    ListAdapter<RecyclerViewRow, ForecastAdapter.ViewHolderPrimary>(ForecastDiffCallback()) {
    var onItemClickListener: OnItemClickListener? = null

    class ForecastViewHolder(val view: View) : ViewHolderPrimary(view) {
        val tvTemp = view.findViewById<TextView>(R.id.tvTemp)
        val tvFeels = view.findViewById<TextView>(R.id.tvDescr)
        val tvDate = view.findViewById<TextView>(R.id.tvDate)
        val descrIcon = view.findViewById<ImageView>(R.id.imIcon)
    }

    class ForecastViewHolderTitle(val view: View) : ViewHolderPrimary(view) {
        val tvTitle = view.findViewById<TextView>(R.id.tvItemTitle)
    }

    open class ViewHolderPrimary(view: View) : RecyclerView.ViewHolder(view) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderPrimary {

        val viewHolder = when (viewType) {
            ITEM_VIEW_TYPE_ROW -> {
                ForecastViewHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.forecast_item, parent, false)
                )

            }

            ITEM_VIEW_TYPE_TITLE -> {
                ForecastViewHolderTitle(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.forecast_item_title, parent, false)
                )
            }

            else -> throw RuntimeException("UNDEFINED VIEWTYPE IN FORECASTADAPTER")
        }
        return viewHolder
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is RecyclerViewItem -> {
                ITEM_VIEW_TYPE_ROW
            }

            is RecyclerViewItemTitle -> {
                ITEM_VIEW_TYPE_TITLE
            }
            else -> throw RuntimeException("ITEM VIEWTYPE IS UNAVIABLE")
        }
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolderPrimary, position: Int) {
        val item = getItem(position)
        when (holder) {
            is ForecastViewHolder -> {
                holder.view.setOnClickListener {
                    onItemClickListener?.itemClick(item)
                }
                if (item is RecyclerViewItem) {
                    holder.tvDate.text = item.dayNumber
                    holder.tvTemp.text = item.temperature + " °C"
                    holder.tvFeels.text =
                        "${item.description}, ощущается как ${item.temperatureFeelsLike} °C"
                    if (item.pictureUrl?.isNotEmpty() == true) {
                        Glide.with(holder.view.context)
                            .load("https://openweathermap.org/img/wn/${item.pictureUrl}@2x.png")
                            .into(holder.descrIcon)
                    }
                }

            }

            is ForecastViewHolderTitle -> {

                holder.view.setOnClickListener {
                    onItemClickListener?.itemClick(item)
                }

            }
            else -> throw RuntimeException("UNDEFINED VIEWHOLDER IN FORECASTADAPTER")

        }
    }

    interface OnItemClickListener {
        fun itemClick(item: RecyclerViewRow) {

        }
    }

    companion object {
        const val ITEM_VIEW_TYPE_TITLE = 1
        const val ITEM_VIEW_TYPE_ROW = 2
        const val MAX_ITEM_POOL_SIZE = 5
    }
}