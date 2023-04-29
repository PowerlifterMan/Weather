package com.example.weather.retrofit.daData

import com.google.gson.annotations.SerializedName

class Suggestions(
    @SerializedName("suggestions")
    val suggestions: MutableList<DadataItemDto>,
)

class DadataItemDto(
    @SerializedName("value")
    val value: String,

    @SerializedName("unrestricted_value")
    val unrestrictedValue: String,

    @SerializedName("data")
    val data: AddressItemDataDto
)

class AddressItemDataDto(
    @SerializedName("country")
    val country: String?,

    @SerializedName("region_with_type")
    val regionWithType: String?,

    @SerializedName("city_with_type")
    val cityWithType: String?,

    @SerializedName("settlement_with_type")
    val settlement_with_type: String?,

    @SerializedName("city_kladr_id")
    val cityKladr_id: String?,

    @SerializedName("geo_lat")
    val latitude: String?,

    @SerializedName("geo_lon")
    val longitude: String?,
)