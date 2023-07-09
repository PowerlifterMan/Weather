package com.example.weather.presentation.main.composeUI

sealed class Screen(
    val route: String
) {

    object Forecast : Screen(ROUTE_FORECAST)
    object ChangeCity : Screen(ROUTE_CHANGE_CITY)
    object ChangeSource : Screen(ROUTE_CHANGE_SOURCE)

    private companion object {

        const val ROUTE_FORECAST = "forecast"
        const val ROUTE_CHANGE_CITY = "change_city"
        const val ROUTE_CHANGE_SOURCE = "change_source"
    }
}