package com.example.weather.retrofit

import com.google.gson.annotations.SerializedName

class CurrentWeatherDto(
    @SerializedName("kind")
    val kind: String,
    @SerializedName("description")
    val description: DescriptionDto,
    @SerializedName("temmperature")
    val temperature: TemperatureDto
)

class TemperatureDto(
    @SerializedName("air")
    val air: FloatValueDto,
    @SerializedName("comfort")
    val comfort: FloatValueDto,
    @SerializedName("water")
    val water: FloatValueDto
)


class FloatValueDto(
    @SerializedName("C")
    val value: Float

)

class AirDto(
    @SerializedName("C")
    val valueC: Float

)

class DescriptionDto(
    @SerializedName("description")
    val full: String
)

