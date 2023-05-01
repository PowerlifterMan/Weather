package com.example.weather.domain

import android.os.Parcel
import android.os.Parcelable


const val DEFAULT_DESCRIPTION = ""

const val DEFAULT_TEMPERATURE = 20f
const val DEFAULT_LOCATION_NAME = "Москва"
const val DEFAULT_COUNTRY = "RU"
const val DEFAULT_LATITUDE = "55.7522"
const val DEFAULT_LONGITUDE = "37.6156"
const val DEFAULT_KLADR_ID = "7700000000000"

data class CurrentCity(
    val fullname: String? = DEFAULT_LOCATION_NAME,
    val name: String? = DEFAULT_LOCATION_NAME,
    val longitude: String? = DEFAULT_LONGITUDE,
    val latitude: String? = DEFAULT_LATITUDE,
    val country: String? = DEFAULT_COUNTRY,
    val cityKladrId: String? = DEFAULT_KLADR_ID

) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(fullname)
        dest.writeString(name)
        dest.writeString(longitude)
        dest.writeString(latitude)
        dest.writeString(country)
        dest.writeString(cityKladrId)

    }

    companion object CREATOR : Parcelable.Creator<CurrentCity> {
        override fun createFromParcel(parcel: Parcel): CurrentCity {
            return CurrentCity(parcel)
        }

        override fun newArray(size: Int): Array<CurrentCity?> {
            return arrayOfNulls(size)
        }
    }

}


data class RecyclerViewItem(
    var dayNumber: String = "",
    val temperature: String = DEFAULT_TEMPERATURE.toString(),
    val temperatureFeelsLike: String = DEFAULT_TEMPERATURE.toString(),
    val description: String = DEFAULT_DESCRIPTION,
    var pictureUrl: String? = null

)
