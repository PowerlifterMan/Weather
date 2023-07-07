package com.example.weather.presentation.main.composeUI.navigationCompose

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun AppNavGraph(
    navhostController: NavHostController,
    mainScreenContent: @Composable() -> Unit,
    sourceScreenContent: @Composable() -> Unit,
    inputPlaceScreenContent: @Composable() -> Unit
) {
    NavHost(
        navController = navhostController,
        startDestination = Screen.MainScreen.route
    ) {
        composable(Screen.MainScreen.route)
        {
            mainScreenContent()
        }
        composable(Screen.InputSourceScreen.route)
        {
            sourceScreenContent()
        }
        composable(Screen.InputPlaceScreen.route)
        {
            inputPlaceScreenContent()
        }
    }
}