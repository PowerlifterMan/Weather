package com.example.weather.data

import com.google.gson.annotations.SerializedName

class GeocodingDTO(
    @SerializedName("name")
    val searchName: String,
    @SerializedName("local_names")
    val local_names: List<LocalNamesDTO>,
    @SerializedName("lat")
    val lat: String,
    @SerializedName("lon")
    val lon: String,
    @SerializedName("country")
    val country: String,
    @SerializedName("state")
    val state: String

)

class LocalNamesDTO(
    @SerializedName("language code")
    val language_code: String,
    @SerializedName("ascii")
    val ascii: String,
    @SerializedName("feature_name")
    val feature_name: String,

    )