package com.example.weather.domain

import android.os.Parcel
import android.os.Parcelable


const val DEFAULT_DESCRIPTION = "none"

const val DEFAULT_TEMPERATURE = 20f
const val DEFAULT_LOCATION_NAME = "Москва"
const val DEFAULT_COUNTRY = "RU"
const val DEFAULT_LATITUDE = "55.7522"
const val DEFAULT_LONGITUDE = "37.6156"

data class CurrentCity(
    val name: String? = DEFAULT_LOCATION_NAME,
    val longitude: String? = DEFAULT_LONGITUDE,
    val latitude: String? = DEFAULT_LATITUDE,
    val country: String? = DEFAULT_COUNTRY

    ) : Parcelable {
    constructor(parcel: Parcel) : this(
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
        dest.writeString(name)
        dest.writeString(longitude)
        dest.writeString(latitude)
        dest.writeString(country)

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
    val description: String = DEFAULT_DESCRIPTION

    )
