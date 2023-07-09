package com.example.weather.presentation.main.composeUI.navigationCompose

sealed class Screen(
    val route: String
) {
    object MainScreen : Screen(ROUTE_MAIN)
    object InputPlaceScreen : Screen(ROUTE_PLACE)
    object ChangeSourceScreen : Screen(ROUTE_SOURCE)

    private companion object {
        const val ROUTE_MAIN = "main"
        const val ROUTE_PLACE = "place"
        const val ROUTE_SOURCE = "source"
    }

}