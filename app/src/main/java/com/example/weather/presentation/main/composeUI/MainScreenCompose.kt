package com.example.weather.presentation.main.composeUI

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.weather.R
import com.example.weather.domain.CurrentCity
import com.example.weather.domain.DEFAULT_KLADR_ID
import com.example.weather.domain.DEFAULT_LOCATION_NAME
import com.example.weather.domain.TempOnTime
import com.example.weather.presentation.main.MainViewModel
import com.example.weather.presentation.main.SOURCE_OPEN_WEATHER
import com.example.weather.presentation.main.composeUI.navigationCompose.AppNavGraph
import com.example.weather.presentation.main.composeUI.navigationCompose.rememberNavigationState
import com.example.weather.presentation.main.recyclerViews.RecyclerViewItem
import com.example.weather.presentation.main.recyclerViews.RecyclerViewItemTitle
import com.example.weather.presentation.main.recyclerViews.RecyclerViewRow
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreenCompose(
    viewModel: MainViewModel
) {
    val navigationState = rememberNavigationState()
    val navHostController = rememberNavController()
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
        /*  bottomBar = {
              BottomNavigation() {
                  val navItems = listOf(
                      NavigationItem.Home,
                      NavigationItem.Favourite,
                      NavigationItem.Profile
                  )
                  navItems.forEach { item ->
                      BottomNavigationItem(
                          selected = false,
                          onClick = { },
                          icon = {
                              Icon(item.icon, contentDescription = null)
                          },
                          label = {
                              Text("$item.titleResId")
                          },
                          selectedContentColor = MaterialTheme.colors.onPrimary,
                          unselectedContentColor = MaterialTheme.colors.onSecondary
                      )

                  }

              }
          }
      */
    ) { paddingValues ->
        AppNavGraph(
            navhostController = navHostController,
            mainScreenContent = { ShowForecastScreenCompose(viewModel = viewModel) },
            sourceScreenContent = { ChangeSourceScreenCompose() },
            inputPlaceScreenContent = { InputPlaceScreenCompose() }
        )
//        ShowForecastScreenCompose(viewModel = viewModel)
    }
}



