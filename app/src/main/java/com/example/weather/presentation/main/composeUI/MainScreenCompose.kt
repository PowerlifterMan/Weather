package com.example.weather.presentation.main.composeUI

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.weather.presentation.main.MainViewModel
import com.example.weather.presentation.main.composeUI.navigationCompose.AppNavGraph
import com.example.weather.presentation.main.composeUI.navigationCompose.rememberNavigationState

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MainScreenCompose(
    viewModel: MainViewModel
) {
    val navHostController = rememberNavController()
    val navigationState = rememberNavigationState()
    val scrollState = rememberScrollState()
    Scaffold(
        modifier = Modifier,
        topBar = {
            val navBackStackEntry by navigationState.navHostController.currentBackStackEntryAsState()
            val currentRout = navBackStackEntry?.destination?.route
            TopAppBar(
//                modifier = Modifier.alignBy(HorizontalAlignment()),
                title = {
                    Text(text = "")
                },
                navigationIcon = {
                    Row(horizontalArrangement = Arrangement.End) {
                        IconButton(onClick = {
                        }) {
                            Icon(Icons.Filled.ArrowBack, "backIcon")
                        }
                        IconButton(onClick = {
                            navigationState.navigateTo(Screen.ChangeSource.route)
                        }) {
                            Icon(Icons.Filled.Search, "Search city")

                        }
                        IconButton(onClick = {
                            navigationState.navigateTo(Screen.ChangeCity.route)
                        }) {
                            Icon(Icons.Filled.Settings, "Change source")
                        }
                    }

                },
            )
        },
    ) {
        AppNavGraph(
            navhostController = navigationState.navHostController,
            mainScreenContent = { ShowForecastScreenCompose(viewModel = viewModel) },
            sourceScreenContent = { ChangeSourceScreenCompose() },
            inputPlaceScreenContent = { InputPlaceScreenCompose() }
        )
    }
}



