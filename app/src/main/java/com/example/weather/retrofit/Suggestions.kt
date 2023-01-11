package com.example.weather.retrofit

import com.example.weather.domain.DayData
import com.google.gson.annotations.SerializedName

class Suggestions(
    @SerializedName("suggestions")
    val suggestions: MutableList<DayData>

    ) {
}