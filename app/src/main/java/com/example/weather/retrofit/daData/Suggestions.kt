package com.example.weather.retrofit.daData

import com.google.gson.annotations.SerializedName

class Suggestions(
    @SerializedName("suggestions")
    val suggestions: MutableList<DadataItemDto>,
)
class DadataItemDto (
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
    @SerializedName("street_with_type")
    val streetWithType: String?,
    @SerializedName("geo_lat")
    val lantitude: String?,
    @SerializedName("geo_lon")
    val lontitude: String?,
)