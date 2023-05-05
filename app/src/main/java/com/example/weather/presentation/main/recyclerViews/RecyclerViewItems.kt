package com.example.weather.presentation.main.recyclerViews

import com.example.weather.domain.DEFAULT_DESCRIPTION
import com.example.weather.domain.DEFAULT_TEMPERATURE

open class RecyclerViewRow

class RecyclerViewItemTitle(
    val title: String = ""
): RecyclerViewRow()

class RecyclerViewItem(
    var dayNumber: String = "",
    val temperature: String = DEFAULT_TEMPERATURE.toString(),
    val temperatureFeelsLike: String = DEFAULT_TEMPERATURE.toString(),
    val description: String = DEFAULT_DESCRIPTION,
    var pictureUrl: String? = null
) : RecyclerViewRow()
